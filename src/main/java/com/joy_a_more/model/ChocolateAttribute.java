package com.joy_a_more.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChocolateAttribute {
    private String chocolateType;
    private String brand;
    private double weightInGrams;
    private int quantity;
    private boolean isSugarFree;
    private double price;
    private Double oldPrice;
    private double discount;
    private String sku;
}
