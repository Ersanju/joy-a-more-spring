package com.joy_a_more.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CakeAttribute {
    private Variant defaultVariant;
    private List<Variant> variants;
    private List<Shape> shapes;
    private boolean isEgglessAvailable;
}
