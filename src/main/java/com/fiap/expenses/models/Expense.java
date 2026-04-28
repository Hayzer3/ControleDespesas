package com.fiap.expenses.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_seq")
    @SequenceGenerator(name = "expense_seq", sequenceName = "expense_seq", allocationSize = 1)
    private Long id;
    private String description;
    private String category;
    private BigDecimal amount;

    @Column(name = "expense_date")
    private LocalDate date;



}
