package com.jb.expense_tracker.service.impl;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jb.expense_tracker.Exceptions.ResourceNotFoundException;
import com.jb.expense_tracker.dto.ExpenseDto;
import com.jb.expense_tracker.entity.Category;
import com.jb.expense_tracker.entity.Expense;
import com.jb.expense_tracker.mapper.ExpenseMapper;
import com.jb.expense_tracker.repository.CategoryRepository;
import com.jb.expense_tracker.repository.ExpenseRepository;
import com.jb.expense_tracker.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService 
{
    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto) 
    {
        Expense expense = ExpenseMapper.mapToExpense(expenseDto);
        Expense savedExpense = expenseRepository.save(expense);
        return ExpenseMapper.mapToExpenseDto(savedExpense);
    }

    @Override
    public ExpenseDto getExpenseById(Long id) 
    {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        return ExpenseMapper.mapToExpenseDto(expense);

    }

    @Override
    public List<ExpenseDto> getAllExpenses() 
    {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                       .map((expense) -> ExpenseMapper.mapToExpenseDto(expense))
                       .collect(Collectors.toList());
    }

    @Override
    public ExpenseDto updateExpenseById(Long id, ExpenseDto expenseDto) 
    {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        expense.setAmount(expenseDto.amount());
        expense.setExpenseDate(expenseDto.expenseDate());
        if(expenseDto.categoryDto() != null)
        {
            Category category = categoryRepository.findById(expenseDto.categoryDto().id()).orElseThrow(() -> new ResourceNotFoundException("category not found with id: " + expenseDto.categoryDto().id()));
            expense.setCategory(category);
        }
        Expense updatedExpense = expenseRepository.save(expense);
        return ExpenseMapper.mapToExpenseDto(updatedExpense);
    }

    @Override
    public void deleteExpenseDto(Long id) 
    {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        expenseRepository.delete(expense);
    }
}//