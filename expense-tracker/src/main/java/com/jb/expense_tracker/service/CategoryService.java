package com.jb.expense_tracker.service;
import java.util.List;

import com.jb.expense_tracker.dto.CategoryDto;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getAllCategory();

}