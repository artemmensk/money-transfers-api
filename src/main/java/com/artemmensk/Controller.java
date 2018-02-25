package com.artemmensk;

import com.artemmensk.account.Account;
import com.artemmensk.account.IAccountService;
import com.artemmensk.exception.AccountNotFound;
import com.artemmensk.exception.Error;
import com.artemmensk.transfer.ITransferService;
import com.google.common.net.MediaType;
import com.google.gson.Gson;
import com.google.inject.Inject;
import org.eclipse.jetty.http.*;
import spark.ResponseTransformer;

import static spark.Spark.*;

public class Controller implements IController {

    private final ITransferService transferService;
    private final IAccountService accountService;

    @Inject
    public Controller(ITransferService transferService, IAccountService accountService) {
        this.transferService = transferService;
        this.accountService = accountService;
    }

    @Override
    public void setUpEndpoints() {
        final Gson gson = new Gson();
        final ResponseTransformer json = gson::toJson;

        post("/account", (req, res) -> {
            final Account account = accountService.create();
            res.status(HttpStatus.CREATED_201);
            res.header(HttpHeader.LOCATION.asString(), req.url() + "/" + account.getId());
            return "";
        });
        get("/account/:id", (req, res) ->  accountService.findById(Long.valueOf(req.params("id"))), json);

        get("/performTransfer/:amount/from/:from/to/:to",
                (req, res) -> transferService.performTransfer(Integer.valueOf(req.params("amount")), Long.valueOf(req.params("from")), Long.valueOf(req.params("to"))));

        after("/*", (req, res) -> res.type(MediaType.JSON_UTF_8.toString()));

        exception(AccountNotFound.class, (ex, req, res) -> {
            res.type(MediaType.JSON_UTF_8.toString());
            res.status(HttpStatus.NOT_FOUND_404);
            res.body(gson.toJson(new Error(ex)));
        });

        exception(Exception.class, (ex, req, res) -> {
            res.type(MediaType.JSON_UTF_8.toString());
            res.status(HttpStatus.BAD_REQUEST_400);
            res.body("{}");
        });
    }
}
