package com.artemmensk;

import com.google.inject.Inject;

import static spark.Spark.get;

public class Controller implements IController {

    private IService service;

    @Inject
    public Controller(IService service) {
        this.service = service;
    }

    @Override
    public void init() {
        get("/transfer/:amount/from/:from/to/:to", (req, res) -> service.transfer(req.params("amount"), req.params("from"), req.params("to")));
    }
}
