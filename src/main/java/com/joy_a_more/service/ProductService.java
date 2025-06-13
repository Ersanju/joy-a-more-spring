package com.joy_a_more.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.joy_a_more.dto.ProductRequest;
import com.joy_a_more.model.Product;
import com.joy_a_more.model.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ProductService {

    private static final String COLLECTION_NAME = "products";

    private final Firestore firestore;

    @Autowired
    public ProductService(Firestore firestore) {
        this.firestore = firestore;
    }

    public static boolean isValid(String url) {
        String regex = "https?://.*\\.(jpg|jpeg|png|gif|webp)";
        return url != null && url.matches(regex);
    }

    public String addProduct(ProductRequest request) throws Exception {
        validateRequest(request);
        checkDuplicate(request);

        Product product = mapToProduct(request);

        DocumentReference docRef = firestore.collection("cake_product").document();
        product.setId(docRef.getId());
        product.setCreatedAt(new Date());

        ApiFuture<WriteResult> writeResult = docRef.set(product);
        return "Product inserted at: " + writeResult.get().getUpdateTime();
    }

    private Product mapToProduct(ProductRequest req) {
        return Product.builder().name(req.getName()).productDescription(req.getProductDescription()).careInstruction(req.getCareInstruction()).deliveryInformation(req.getDeliveryInformation()).categoryId(req.getCategoryId()).imageUrls(req.getImageUrls()).isAvailable(req.isAvailable()).stockQuantity(req.getStockQuantity()).tags(req.getTags()).popularityScore(0).extraAttributes(req.getExtraAttributes()).reviews(List.of())  // Optional: default empty
                .build();
    }

    private void checkDuplicate(ProductRequest request) throws Exception {
        ApiFuture<QuerySnapshot> query = firestore.collection("products").whereEqualTo("name", request.getName()).get();
        List<QueryDocumentSnapshot> docs = query.get().getDocuments();

        for (QueryDocumentSnapshot doc : docs) {
            Product existing = doc.toObject(Product.class);
            if (existing.getName().equalsIgnoreCase(request.getName())) {
                throw new Exception("Duplicate product name");
            }
            if (existing.getExtraAttributes() != null) {
                for (Variant oldVar : existing.getExtraAttributes().getVariants()) {
                    for (Variant newVar : request.getExtraAttributes().getVariants()) {
                        if (oldVar.getSku().equalsIgnoreCase(newVar.getSku())) {
                            throw new Exception("Duplicate SKU: " + newVar.getSku());
                        }
                    }
                }
            }
        }
    }

    private void validateRequest(ProductRequest req) throws Exception {
        for (String url : req.getImageUrls()) {
            if (!isValid(url)) {
                throw new Exception("Invalid image URL: " + url);
            }
        }
        if (req.getExtraAttributes().getDefaultVariant() == null) throw new Exception("Default variant is required");

        if (req.getExtraAttributes().getVariants().isEmpty()) throw new Exception("At least one variant is required");

        for (Variant var : req.getExtraAttributes().getVariants()) {
            if (var.getSku() == null || var.getSku().isEmpty()) throw new Exception("SKU must not be empty");
            if (var.getPrice() <= 0) throw new Exception("Price must be positive");
        }
    }

    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().length() < 3) {
            throw new IllegalArgumentException("Name must be at least 3 characters");
        }

        if (product.getImageUrls() == null || product.getImageUrls().isEmpty()) {
            throw new IllegalArgumentException("At least one image URL is required");
        }

        Pattern urlPattern = Pattern.compile("^https?://.*");
        for (String url : product.getImageUrls()) {
            if (!urlPattern.matcher(url).matches()) {
                throw new IllegalArgumentException("Invalid image URL: " + url);
            }
        }

        if (product.getExtraAttributes() == null || product.getExtraAttributes().getVariants() == null) {
            throw new IllegalArgumentException("At least one variant is required");
        }

        if (product.getExtraAttributes().getDefaultVariant() == null) {
            throw new IllegalArgumentException("Default variant must be specified");
        }

        if (product.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
    }


    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();

        try {
            ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                Product product = doc.toObject(Product.class);
                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
}
