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
    private List<String> productDescription;
    private List<String> careInstruction;
    private List<String> deliveryInformation;
    private String categoryId;
    private List<String> imageUrls;
    private boolean isAvailable;
    private int stockQuantity;
    private List<String> tags;
    private int popularityScore;
    private List<Review> reviews;
    private ExtraAttributes extraAttributes;
    private Date createdAt;
}
