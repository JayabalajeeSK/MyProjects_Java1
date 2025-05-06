package com.jb.expense_tracker.dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Category Data Transfer Object")
public record CategoryDto(
    @Schema(description = "Unique identifier of the category")
    Long id,

    @Schema(description = "Name of the category")
    String name
) 
{

}