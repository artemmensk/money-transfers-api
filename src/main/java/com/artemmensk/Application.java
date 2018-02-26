package com.artemmensk;

import com.artemmensk.account.AccountModule;
import com.artemmensk.transfer.TransferModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Application enter point.
 */
public class Application {
    public static void main( String[] args ) {
        final Injector injector = Guice.createInjector(new ControllerModule(), new AccountModule(), new TransferModule());
        injector.getInstance(IController.class).setUpEndpoints();
    }
}
