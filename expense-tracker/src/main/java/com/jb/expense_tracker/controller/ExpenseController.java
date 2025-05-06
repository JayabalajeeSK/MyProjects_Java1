package com.jb.expense_tracker.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jb.expense_tracker.dto.ExpenseDto;
import com.jb.expense_tracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

@Tag(name = "Expense API", description = "Operations related to expense management")
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // Create expense
    @Operation(
        summary = "Create a new expense",
        description = "Create a new expense by providing expense details",
        responses = {
            @ApiResponse(
                responseCode = "201", 
                description = "Expense created successfully", 
                content = @Content(mediaType = "application/json")
            )
        }
    )
    @PostMapping
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseDto expenseDto) {
        ExpenseDto savedExpense = expenseService.createExpense(expenseDto);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    // Get expense by ID
    @Operation(
        summary = "Get expense by ID",
        description = "Retrieve an expense by providing the expense ID",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Expense retrieved successfully", 
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Expense not found", 
                content = @Content(mediaType = "application/json")
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable("id") Long id) {
        ExpenseDto expenseDto = expenseService.getExpenseById(id);
        return new ResponseEntity<>(expenseDto, HttpStatus.OK);
    }

    // Get all expenses
    @Operation(
        summary = "Get all expenses",
        description = "Retrieve the list of all expenses",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "List of expenses retrieved successfully", 
                content = @Content(mediaType = "application/json")
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getAllExpenses() {
        List<ExpenseDto> expenses = expenseService.getAllExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    // Update expense by ID
    @Operation(
        summary = "Update expense by ID",
        description = "Update an existing expense by providing the expense ID and new details",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Expense updated successfully", 
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Expense not found", 
                content = @Content(mediaType = "application/json")
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable("id") Long id, @RequestBody ExpenseDto expenseDto) {
        ExpenseDto updatedExpense = expenseService.updateExpenseById(id, expenseDto);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    // Delete expense by ID
    @Operation(
        summary = "Delete expense by ID",
        description = "Delete an expense by providing the expense ID",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Expense deleted successfully", 
                content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Expense not found", 
                content = @Content(mediaType = "application/json")
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable("id") Long expenseId) {
        expenseService.deleteExpenseDto(expenseId);
        return new ResponseEntity<>("Expense deleted successfully", HttpStatus.OK);
    }
}