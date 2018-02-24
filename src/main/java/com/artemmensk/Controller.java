package com.artemmensk;

import com.artemmensk.account.IAccountService;
import com.artemmensk.transfer.ITransferService;
import com.google.inject.Inject;

import static spark.Spark.get;

public class Controller implements IController {

    private ITransferService transferService;
    private IAccountService accountService;

    @Inject
    public Controller(ITransferService transferService, IAccountService accountService) {
        this.transferService = transferService;
        this.accountService = accountService;
    }

    @Override
    public void setUpEndpoints() {
        get("/account/:id", (req, res) -> accountService.findById(Long.valueOf(req.params("id"))));
        get("/transfer/:amount/from/:from/to/:to",
                (req, res) -> transferService.transfer(Integer.valueOf(req.params("amount")), Long.valueOf(req.params("from")), Long.valueOf(req.params("to"))));
    }
}
