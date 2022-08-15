package com.kata.app.util;

import com.kata.app.model.Account;
import com.kata.app.model.Amount;
import com.kata.app.model.Transaction;
import com.kata.app.model.TransactionType;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class OperationUtilTest {

    @Test
    public void testMakeDeposit() {
        Account account = new Account();
        Amount firstAmount = new Amount();
        Amount secondAmount = new Amount();
        firstAmount.setValue(BigDecimal.valueOf(1000));
        secondAmount.setValue(BigDecimal.valueOf(1000));
        Transaction transaction = new Transaction();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        final LocalDateTime dateTime = LocalDateTime.now();
        transaction.setDate(formatter.format(dateTime));

        transaction.setType(TransactionType.DEPOSIT);

        transaction.setAmount(firstAmount.getValue());
        transaction.setAmount(secondAmount.getValue());

        account.setBalance(account.getBalance().add(firstAmount.getValue()));
        account.setBalance(account.getBalance().add(secondAmount.getValue()));
        BigDecimal expectedResult = BigDecimal.valueOf(2000);
        BigDecimal Result = account.getBalance();

        assertEquals(expectedResult, Result);
    }

    @Test
    public void testMakeWithdrawl() {
        Account account = new Account();
        Amount firstAmount = new Amount();
        Amount secondAmount = new Amount();
        account.setBalance(BigDecimal.valueOf(3000));
        firstAmount.setValue(BigDecimal.valueOf(1000));
        secondAmount.setValue(BigDecimal.valueOf(1000));
        Transaction transaction = new Transaction();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        final LocalDateTime dateTime = LocalDateTime.now();
        transaction.setDate(formatter.format(dateTime));

        transaction.setType(TransactionType.WITHDRAWAL);

        transaction.setAmount(firstAmount.getValue());
        transaction.setAmount(secondAmount.getValue());

        account.setBalance(account.getBalance().subtract(firstAmount.getValue()));
        account.setBalance(account.getBalance().subtract(secondAmount.getValue()));
        BigDecimal Result = account.getBalance();

        BigDecimal expectedResult = BigDecimal.valueOf(1000);

        assertEquals(expectedResult, Result);
    }

    @Test(expected = NumberFormatException.class)
    public void testWithdrawlAmountCanBeBiggerThanBalance() {
        Amount amount = new Amount();
        amount.setValue(BigDecimal.valueOf(1000));
        OperationUtil.makeWithdrawl(amount);

    }

    @Test(expected = NumberFormatException.class)
    public void testIfAmountIsNegativeWhenDeposit() {
        Amount amount = new Amount();
        amount.setValue(BigDecimal.valueOf(-1000));
        OperationUtil.makeDeposit(amount);

    }
}
