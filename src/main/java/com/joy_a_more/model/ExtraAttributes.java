package com.joy_a_more.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtraAttributes {
    private CakeAttribute cakeAttribute;
    private ToyAttribute toyAttribute;
    private GiftAttribute giftAttribute;
    private ChocolateAttribute chocolateAttribute;
    private DecorationAttribute decorationAttribute;
}
