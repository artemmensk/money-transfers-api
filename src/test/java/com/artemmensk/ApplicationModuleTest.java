package com.artemmensk;

import com.artemmensk.account.IAccountService;
import com.artemmensk.transfer.ITransferService;
import com.google.inject.AbstractModule;

import static org.mockito.Mockito.mock;

public class ApplicationModuleTest extends AbstractModule {
    @Override
    protected void configure() {
        bind(IAccountService.class).toInstance(mock(IAccountService.class));
        bind(ITransferService.class).toInstance(mock(ITransferService.class));
    }
}
