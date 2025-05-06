package com.jb.expense_tracker.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jb.expense_tracker.dto.CategoryDto;
import com.jb.expense_tracker.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController 
{
    @Autowired
    private CategoryService categoryService;

    //create category
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto)
    {
        CategoryDto category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    //get Category by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryDto(@PathVariable("id") Long id)
    {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    //get all categories
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories()
    {
        List<CategoryDto> categories = categoryService.getAllCategory();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

}