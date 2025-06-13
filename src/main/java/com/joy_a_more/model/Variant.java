package com.joy_a_more.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Variant {
    private String weight;
    private int tier;
    private double price;
    private Double oldPrice;
    private Double discount;
    private String sku;
}
