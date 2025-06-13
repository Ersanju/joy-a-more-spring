package com.joy_a_more.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExtraAttributes {
    private Variant defaultVariant;
    private List<Variant> variants;
    private List<Shape> shapes;
}
