package com.jb.expense_tracker.controller;
import com.jb.expense_tracker.dto.CategoryDto;
import com.jb.expense_tracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;

@Tag(name = "Category API", description = "Operations related to category management")

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Create category
    @Operation(
        summary = "Create a new category",
        description = "Create a new category by providing category details",
        responses = {
            @ApiResponse(
                responseCode = "201", 
                description = "Category created successfully", 
                content = @Content(mediaType = "application/json")
            )
        }
    )
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    // Get category by ID
    @Operation(
        summary = "Get category by ID",
        description = "Fetch category details by providing the category ID",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Category retrieved successfully", 
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Category not found", 
                content = @Content(mediaType = "application/json")
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryDto(@PathVariable("id") Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    // Get all categories
    @Operation(
        summary = "Get all categories",
        description = "Retrieve the list of all categories",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "List of categories retrieved successfully", 
                content = @Content(mediaType = "application/json")
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategory();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Update category by ID
    @Operation(
        summary = "Update category by ID",
        description = "Update an existing category by providing the category ID and new details",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Category updated successfully", 
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Category not found", 
                content = @Content(mediaType = "application/json")
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }

    // Delete category by ID
    @Operation(
        summary = "Delete category by ID",
        description = "Delete a category by providing the category ID",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Category deleted successfully", 
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Category not found", 
                content = @Content(mediaType = "application/json")
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category with ID " + id + " deleted successfully.");
    }
}//