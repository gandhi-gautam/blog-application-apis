package com.blogapp.controllers;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.payloads.PageResponse;
import com.blogapp.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    ResponseEntity<?> createNewCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategoryDto = categoryService.createNewCategory(categoryDto);
        return new ResponseEntity<>(createdCategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable int categoryId) {
        CategoryDto upadtedCategoryDto = categoryService.updateCategory(categoryDto, categoryId);
        return ResponseEntity.ok(upadtedCategoryDto);
    }

    @GetMapping("/{categoryId}")
    ResponseEntity<?> getCategory(@PathVariable int categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping
    ResponseEntity<?> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "2", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "categoryId", required = false) String fieldName,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageResponse categories = categoryService.getAllCategories(pageNumber, pageSize, fieldName, sortDir);
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{categoryId}")
    ResponseEntity<?> deleteCategory(@PathVariable int categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category Deleted Successfully", true),
                HttpStatus.OK);
    }
}
