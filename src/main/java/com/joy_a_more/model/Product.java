package com.joy_a_more.model;

import lombok.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private String id;
    private String name;
    private List<String> subCategoryIds;
    private String productType;
    private List<String> imageUrls;
    private List<String> productDescription;
    private List<String> careInstruction;
    private List<String> deliveryInformation;
    private List<String> tags;
    private boolean isAvailable;
    private int stockQuantity;
    private int popularityScore;
    private List<Review> reviews;
    private ExtraAttributes extraAttributes;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
}
