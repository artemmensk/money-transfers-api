package com.artemmensk;

import com.google.inject.AbstractModule;

public class TransferModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IController.class).to(Controller.class);
        bind(IService.class).to(Service.class);
    }
}
