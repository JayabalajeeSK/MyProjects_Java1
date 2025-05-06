package com.jb.expense_tracker.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Expense Data Transfer Object")
public record ExpenseDto(
    @Schema(description = "Unique identifier of the expense")
    Long id,

    @Schema(description = "Amount of the expense")
    BigDecimal amount,

    @Schema(description = "Date when the expense occurred")
    LocalDate expenseDate,

    @Schema(description = "Category associated with the expense")
    CategoryDto categoryDto
) 
{

}//