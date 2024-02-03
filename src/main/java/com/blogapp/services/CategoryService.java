package com.blogapp.services;

import com.blogapp.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createNewCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);

    CategoryDto getCategoryById(int categoryId);

    void deleteCategoryById(int categoryId);

    List<CategoryDto> getAllCategories();
}
