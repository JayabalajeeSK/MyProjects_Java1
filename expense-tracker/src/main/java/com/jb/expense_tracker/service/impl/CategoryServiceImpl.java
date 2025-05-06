package com.jb.expense_tracker.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jb.expense_tracker.dto.CategoryDto;
import com.jb.expense_tracker.entity.Category;
import com.jb.expense_tracker.mapper.CategoryMapper;
import com.jb.expense_tracker.repository.CategoryRepository;
import com.jb.expense_tracker.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) 
    {
        Category category = CategoryMapper.mapToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.mapToCategoryDto(savedCategory);
    }

}