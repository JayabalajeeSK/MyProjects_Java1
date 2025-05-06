package com.jb.expense_tracker.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jb.expense_tracker.dto.ExpenseDto;
import com.jb.expense_tracker.entity.Expense;
import com.jb.expense_tracker.mapper.ExpenseMapper;
import com.jb.expense_tracker.repository.ExpenseRepository;
import com.jb.expense_tracker.service.ExpenseService;
@Service
public class ExpenseServiceImpl implements ExpenseService 
{
    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto) 
    {
        Expense expense = ExpenseMapper.mapToExpense(expenseDto);
        Expense savedExpense = expenseRepository.save(expense);
        return ExpenseMapper.mapToExpenseDto(savedExpense);
    }
}
