package com.blogapp.services;

import com.blogapp.payloads.CategoryDto;
import com.blogapp.payloads.PageResponse;

public interface CategoryService {
    CategoryDto createNewCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);

    CategoryDto getCategoryById(int categoryId);

    void deleteCategoryById(int categoryId);

    PageResponse getAllCategories(int pageNumber, int pageSize, String fieldName, String sortDir);
}
