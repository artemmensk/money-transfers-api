package com.artemmensk;

import com.artemmensk.account.Account;
import com.artemmensk.account.IAccountService;
import com.artemmensk.exception.*;
import com.artemmensk.exception.Error;
import com.artemmensk.transfer.ITransferService;
import com.artemmensk.transfer.Transfer;
import com.google.common.net.MediaType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import lombok.extern.log4j.Log4j;
import org.eclipse.jetty.http.*;
import spark.ResponseTransformer;

import static spark.Spark.*;

/**
 * Spark based RESTful mapping on {@link ITransferService} and {@link IAccountService}
 */
@Log4j
public class Controller implements IController {
    private final ITransferService transferService;
    private final IAccountService accountService;

    private final Gson gson = new Gson();
    private final ResponseTransformer json = gson::toJson;
    private final JsonParser jsonParser = new JsonParser();

    @Inject
    public Controller(ITransferService transferService, IAccountService accountService) {
        this.transferService = transferService;
        this.accountService = accountService;
    }

    @Override
    public void setUpEndpoints() {

        log.info("Setting up endpoints...");

        post("/account", (req, res) -> {
            final Account account = accountService.create();
            res.status(HttpStatus.CREATED_201);
            res.header(HttpHeader.LOCATION.asString(), req.url() + "/" + account.getId());
            return "";
        });
        put("/account/:id", (req, res) -> {
            final JsonObject object = jsonParser.parse(req.body()).getAsJsonObject();
            accountService.deposit(object.get("amount").getAsInt(), Long.valueOf(req.params("id")));
            res.status(HttpStatus.NO_CONTENT_204);
            res.header(HttpHeader.LOCATION.asString(), req.url());
            return "";
        });
        get("/account/:id", (req, res) -> accountService.findById(Long.valueOf(req.params("id"))), json);
        get("/account/:id/transfer", (req, res) -> transferService.getTransfersForAccount(Long.valueOf(req.params("id"))), json);

        post("/transfer",
                (req, res) -> {
                    final JsonObject object = jsonParser.parse(req.body()).getAsJsonObject();
                    final Transfer transfer = transferService.performTransfer(
                            object.get("amount").getAsInt(),
                            object.get("source").getAsLong(),
                            object.get("destination").getAsLong()
                    );
                    res.status(HttpStatus.CREATED_201);
                    res.header(HttpHeader.LOCATION.asString(), req.url() + "/" + transfer.getUuid());
                    return "";
                });
        get("/transfer/:uuid", (req, res) -> transferService.getTransfer(req.params("uuid")), json);
        get("/transfer", (req, res) -> transferService.getAllTransfers(), json);

        after("/*", (req, res) -> res.type(MediaType.JSON_UTF_8.toString()));

        mapStatusCode(HttpStatus.CONFLICT_409, NotEnoughBalance.class);
        mapStatusCode(HttpStatus.NOT_FOUND_404, TransferNotFound.class, AccountNotFound.class);
        mapStatusCode(HttpStatus.BAD_REQUEST_400, NegativeDeposit.class, TheSameAccount.class, Exception.class);

        log.info("Endpoints were set up");
    }

    private void mapStatusCode(int status, Class<? extends Exception>... exceptions) {
        for (Class<? extends Exception> exception : exceptions) {
            exception(exception, (ex, req, res) -> {
                res.type(MediaType.JSON_UTF_8.toString());
                res.status(status);
                res.body(gson.toJson(new Error(ex)));
            });
        }
    }
}
