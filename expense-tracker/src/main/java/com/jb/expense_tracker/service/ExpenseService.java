package com.jb.expense_tracker.service;
import com.jb.expense_tracker.dto.ExpenseDto;

public interface ExpenseService 
{
    ExpenseDto createExpense(ExpenseDto expenseDto);
	

}