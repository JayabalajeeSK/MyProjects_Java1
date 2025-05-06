package com.jb.expense_tracker.service.impl;
import java.util.List;
import java.util.stream.Collectors;
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

    @Override
    public CategoryDto getCategoryById(Long id) 
    {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category Not Found with id: "+ id));
        return CategoryMapper.mapToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() 
    {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                         .map((Category) -> CategoryMapper.mapToCategoryDto(Category))
                         .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) 
    {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category Not Found with id: "+ id));
        category.setName(categoryDto.name());
        Category updatedCategory = categoryRepository.save(category);
        return CategoryMapper.mapToCategoryDto(updatedCategory);
    }

    @Override
    public CategoryDto deleteCategory(Long id) 
    {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category Not Found with id: "+ id));
        categoryRepository.delete(category);
        return CategoryMapper.mapToCategoryDto(category);
    }

}