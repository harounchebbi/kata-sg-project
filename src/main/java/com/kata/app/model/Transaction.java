package com.kata.app.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * This is the Transaction Class
 */
@Getter
@Setter
public class Transaction {
  private String date;
  private TransactionType type;
  private BigDecimal amount;

}
