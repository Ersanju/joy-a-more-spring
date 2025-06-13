package com.joy_a_more.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private String userId;
    private String userName;
    private List<String> imageUrls;
    private double rating;
    private String comment;
    private String occasion;
    private String place;
    private Date createdAt;
}
