package com.joy_a_more.dto;

import com.joy_a_more.model.ExtraAttributes;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name length must be between 3 and 100 characters")
    private String name;

    @NotEmpty(message = "Product description is required")
    private List<@NotBlank String> productDescription;

    private List<@NotBlank String> careInstruction;
    private List<@NotBlank String> deliveryInformation;

    @NotBlank(message = "Category ID is required")
    private String categoryId;

    @NotEmpty(message = "At least one image URL is required")
    private List<@Pattern(regexp = "https?://.*\\.(jpg|jpeg|png|gif|webp)", message = "Invalid image URL format") String> imageUrls;

    private boolean isAvailable;

    @Min(value = 0, message = "Stock must be non-negative")
    private int stockQuantity;

    private List<String> tags;

    @NotNull(message = "Extra attributes are required")
    private ExtraAttributes extraAttributes;

}
