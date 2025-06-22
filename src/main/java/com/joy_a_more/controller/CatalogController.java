package com.joy_a_more.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/admin/catalog")
@CrossOrigin(origins = "*")
public class CatalogController {

    private static final String COLLECTION_NAME = "catalog";

    @PostMapping("/add")
    public String insertCatalog(@RequestBody Map<String, Object> catalogJson) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        if (!catalogJson.containsKey("categoryId")) {
            return "Missing categoryId field";
        }

        String categoryId = catalogJson.get("categoryId").toString();

        // Calculate productCount dynamically
        if (catalogJson.containsKey("subcategories")) {
            List<Map<String, Object>> subcategories = (List<Map<String, Object>>) catalogJson.get("subcategories");

            for (Map<String, Object> subcategory : subcategories) {
                if (subcategory.containsKey("products")) {
                    List<Map<String, Object>> products = (List<Map<String, Object>>) subcategory.get("products");
                    subcategory.put("productCount", products.size());
                } else {
                    subcategory.put("productCount", 0);
                }
            }
        }

        catalogJson.put("createdAt", System.currentTimeMillis());

        DocumentReference docRef = db.collection(COLLECTION_NAME).document(categoryId);
        docRef.set(catalogJson).get();

        return "Catalog inserted successfully with ID: " + categoryId;
    }

    @GetMapping("/get")
    public List<Map<String, Object>> getCatalog() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference catalogRef = db.collection(COLLECTION_NAME);

        ApiFuture<QuerySnapshot> querySnapshot = catalogRef.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Map<String, Object>> catalogList = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            Map<String, Object> data = document.getData();
            catalogList.add(data);
        }
        return catalogList;
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getCatalogById(@PathVariable String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot document = docRef.get().get();

        if (document.exists()) {
            return document.getData();
        } else {
            return Map.of("error", "Document not found with ID: " + id);
        }
    }


}
