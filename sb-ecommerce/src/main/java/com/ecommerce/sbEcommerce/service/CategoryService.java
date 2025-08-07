package com.ecommerce.sbEcommerce.service;

import com.ecommerce.sbEcommerce.payload.CategoryDTO;
import com.ecommerce.sbEcommerce.payload.CategoryResponse;


public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber , Integer pageSize , String sortBy , String sortOrder);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO  deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
