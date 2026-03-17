package com.fiap.expenses.service;

import com.fiap.expenses.models.Expense;
import com.fiap.expenses.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository repository;

    public List<Expense> getExpenses(){
        return repository.findAll();
    }

    public Expense addExpense(Expense expense){
        return repository.save(expense);
    }

    public Optional<Expense> getExpenseById(Long id) {
        return repository.findById(id);
    }

    public void deleteExpense(Long id) {
        var optionalExpense = getExpenseById(id);
        if (optionalExpense.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Despesa não encontrada");
        }

        repository.deleteById(id);
    }

    public Expense updateExpense(Long id, Expense newExpense) {
        var optionalExpense  = getExpenseById(id);
        if (optionalExpense.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Despesa não encontrada");
        }

        newExpense.setId(id);
        return repository.save(newExpense);

    }
}
