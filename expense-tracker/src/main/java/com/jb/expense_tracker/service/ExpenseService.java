package com.jb.expense_tracker.service;
import java.util.List;
import com.jb.expense_tracker.dto.ExpenseDto;

public interface ExpenseService 
{
    ExpenseDto createExpense(ExpenseDto expenseDto);

    ExpenseDto getExpenseById(Long id);

    List<ExpenseDto> getAllExpenses();
	

}