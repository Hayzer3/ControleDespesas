package com.fiap.expenses.controllers;

import com.fiap.expenses.models.Expense;
import com.fiap.expenses.service.ExpenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("expenses")
public class ExpenseController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExpenseService service;

    @GetMapping
    public List<Expense> getExpenses(){
        log.info("Listando todos os despesas");
        return service.getExpenses();
    }

    @PostMapping
    public ResponseEntity<Expense> addMovie(@RequestBody Expense expense){ //Binding do JSON para o objeto Movie
        log.info("Cadastrando despesa...");
        var expenses = service.addExpense(expense);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(expenses);
    }

    @GetMapping("{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id){
        log.info("Buscando filme com id {}", id);
        return service.getExpenseById(id)
                .map(ResponseEntity::ok) //reference method
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id){
        service.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense newExpense){
        Expense expense = service.updateExpense(id, newExpense);
        return ResponseEntity.ok(expense);
    }
}
