package com.kata.app.util;

import com.kata.app.base.Constants;
import com.kata.app.model.Account;
import com.kata.app.model.Amount;
import com.kata.app.model.Transaction;
import com.kata.app.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;


/**
 * This is the OperationUtil class that contains utility methods for this application
 */
public class OperationUtil {
    static Account account = new Account();

    private static final Logger LOGGER = Logger.getLogger(OperationUtil.class.getName());


    private OperationUtil() {
    }

    /**
     * This is the method to make a deposit in your account
     *
     * @param amount
     */
    public static void makeDeposit(Amount amount) {
        OperationUtil.verifyAmount(amount);

        Transaction transaction = OperationUtil.setTransaction(amount, TransactionType.DEPOSIT);

        account.setBalance(account.getBalance().add(amount.getValue()));
        //add transaction to history
        account.getHistory().add(transaction);

        LOGGER.info("Your new balance is" + account.getBalance() + "");

    }

    /**
     * This is the method to make a withdrawl from your account
     *
     * @param amount
     * @throws NumberFormatException
     */
    public static void makeWithdrawl(Amount amount) throws NumberFormatException {
        OperationUtil.verifyAmount(amount);

        if (amount.getValue().compareTo(account.getBalance()) > 0) throw new NumberFormatException();


        Transaction transaction = OperationUtil.setTransaction(amount, TransactionType.WITHDRAWAL);

        account.setBalance(account.getBalance().subtract(amount.getValue()));
        //add transaction to history
        account.getHistory().add(transaction);

        LOGGER.info("Your new balance is" + account.getBalance() + "");

    }

    /**
     * This is the method to show transactions history of your account
     *
     * @return
     */
    public static List<Transaction> showHistory() {
        LOGGER.info("Here is your account history:");
        List<Transaction> transactions = account.getHistory();
        for (Transaction operation : transactions) {
            LOGGER.info("Date: " + operation.getDate()
                    + ", Type: " + operation.getType()
                    + ", Balance: €" + operation.getAmount());
        }
        LOGGER.info("Your current balance is €" + account.getBalance());
        return transactions;
    }

    /**
     * This is the method to get the total balance of your account
     *
     * @return
     */
    public static BigDecimal totalBalance() {
        return account.getBalance();
    }


    /**
     * This is the method to verify the amount entered by the user
     *
     * @param amount
     */
    public static void verifyAmount(Amount amount) {
        if (amount == null)
            throw new NumberFormatException(Constants.AMOUNT_CANNOT_BE_NULL);
        else if (amount.getValue().compareTo(BigDecimal.ZERO) <= 0)
            throw new NumberFormatException(Constants.AMOUNT_MUST_BE_GREATER_THAN_ZERO);
        else if (amount.getValue().scale() > 2)
            throw new NumberFormatException(Constants.AMOUNT_CANNOT_HAVE_MORE_THAN_TWO_DIGITS);
    }

    /**
     * This is the method to set the transaction date , type and amount
     *
     * @param amount
     * @param deposit
     * @return
     */
    public static Transaction setTransaction(Amount amount, TransactionType deposit) {
        Transaction transaction = new Transaction();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
        final LocalDateTime dateTime = LocalDateTime.now();
        transaction.setDate(formatter.format(dateTime));

        transaction.setType(deposit);

        transaction.setAmount(amount.getValue());
        return transaction;
    }
}
