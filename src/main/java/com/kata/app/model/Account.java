package com.kata.app.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the Account class
 */
@Getter
@Setter
public class Account {
    private BigDecimal balance;
    private List<Transaction> history;

    public Account() {
        this.balance = BigDecimal.ZERO;
        history = new ArrayList<>();
    }
}
