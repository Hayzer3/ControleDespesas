package com.fiap.expenses.repositories;

import com.fiap.expenses.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
