package com.artemmensk;

import com.artemmensk.account.IAccountService;
import com.artemmensk.transfer.ITransferService;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import static org.mockito.Mockito.mock;

public class ControllerModuleMock extends AbstractModule {
    @Override
    protected void configure() {
        bind(IController.class).to(Controller.class).in(Singleton.class);
        bind(IAccountService.class).toInstance(mock(IAccountService.class));
        bind(ITransferService.class).toInstance(mock(ITransferService.class));
    }
}
