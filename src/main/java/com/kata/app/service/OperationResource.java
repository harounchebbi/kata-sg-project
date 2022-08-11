package com.kata.app.service;


import com.kata.app.base.Constants;
import com.kata.app.model.Amount;
import com.kata.app.model.Response;
import com.kata.app.model.Transaction;
import com.kata.app.util.OperationUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.List;


/**
 * This is the OperationResource class that contains all rest api methods
 */
@Path("/operation") // This means URL's start with /operation (after Application path)
public class OperationResource { // This means that this class is a Resource

    @GET // this means the http method type
    @Path("/history")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> showAccountHistory() {
        return OperationUtil.showHistory();
    }

    @GET // this means the http method type
    @Path("/balance/total")
    @Produces(MediaType.APPLICATION_JSON)
    public BigDecimal getTotalBalance() {
        return OperationUtil.totalBalance();
    }

    @PUT
    @Path("/deposit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeDeposit(Amount amount) {
        OperationUtil.makeDeposit(amount);
        return new Response(true, "deposit of" + amount.getValue() + " successful");
    }

    @POST
    @Path("/withdraw")
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeWithdraw(Amount amount) {
        try {
            OperationUtil.makeWithdrawl(amount);
        } catch (NumberFormatException e) {
            return new Response(false, Constants.AMOUNT_CANNOT_BE_GREATER_THAN_BALANCE);
        }

        return new Response(true, "withdrawl of" + amount.getValue() + " successful");
    }
}
