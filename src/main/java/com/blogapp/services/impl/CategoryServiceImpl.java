package com.blogapp.services.impl;

import com.blogapp.entities.Category;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.payloads.PageResponse;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createNewCategory(CategoryDto categoryDto) {
        Category category = dtoToCategory(categoryDto);
        Category createdCategory = categoryRepo.save(category);
        return categoryToDto(createdCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "categoryId", categoryId));
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        Category updatedCategory = categoryRepo.save(category);
        return categoryToDto(updatedCategory);
    }

    @Override
    public CategoryDto getCategoryById(int categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "categoryId", categoryId));
        return categoryToDto(category);
    }

    @Override
    public void deleteCategoryById(int categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepo.delete(category);
    }

    @Override
    public PageResponse getAllCategories(int pageNumber, int pageSize) {
        Pageable pageable = createPageableRequest(pageNumber, pageSize);
        Page<Category> categories = categoryRepo.findAll(pageable);
        List<CategoryDto> categoryDtos = categories.getContent().stream().map(category -> categoryToDto(category)).
                collect(Collectors.toList());
        PageResponse pageResponse = mapPageWithData(categories, categoryDtos);
        return pageResponse;
    }

    private Category dtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    private CategoryDto categoryToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    private Pageable createPageableRequest(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber, pageSize);
    }

    private PageResponse mapPageWithData(Page<Category> categories, List<CategoryDto> categoryDtos) {
        PageResponse pageResponse = new PageResponse();
        pageResponse.setContent(categoryDtos);
        pageResponse.setTotalPages(categories.getTotalPages());
        pageResponse.setLastPage(categories.isLast());
        pageResponse.setPageSize(categories.getSize());
        pageResponse.setTotalElements(categories.getTotalElements());
        pageResponse.setPageNumber(categories.getNumber());
        return pageResponse;
    }
}
