package com.blogapp.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto {
    private int categoryId;
    @NotBlank
    @Size(min = 4, message = "Category Title must be of atleast 4 characters!")
    private String categoryTitle;
    @NotBlank
    @Size(min = 10, message = "Category Description must be of atleast 4 characters!")
    private String categoryDescription;
}
