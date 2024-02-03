package com.blogapp.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryDto {
    private int categoryId;
    @NotEmpty
    private String categoryTitle;
    @NotEmpty
    @Size(min = 2, message = "Category description must contains atlest 2 characters")
    private String categoryDescription;
}
