package com.joy_a_more.service;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.joy_a_more.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProductService {

    private static final String COLLECTION_NAME = "products";

    public String addMultipleProducts(List<Product> products) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        WriteBatch batch = db.batch();

        List<String> insertedIds = new ArrayList<>();

        for (Product product : products) {
            if (product.getId() == null || product.getId().isEmpty()) {
                return "Each product must have a non-empty ID";
            }
            product.setCreatedAt(new Date());
            DocumentReference docRef = db.collection(COLLECTION_NAME).document(product.getId());
            batch.set(docRef, product);
            insertedIds.add(product.getId());
        }
        batch.commit().get();

        StringBuilder response = new StringBuilder();
        response.append("Inserted ").append(insertedIds.size()).append(" products with IDs:\n");
        for (String id : insertedIds) {
            response.append(id).append("\n");
        }

        return response.toString();
    }
}
