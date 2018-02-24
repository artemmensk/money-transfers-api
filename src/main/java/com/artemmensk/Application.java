package com.artemmensk;

import com.artemmensk.account.AccountModule;
import com.artemmensk.transfer.TransferModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Application {
    public static void main( String[] args ) {
        Injector injector = Guice.createInjector(new ApplicationModule(), new AccountModule(), new TransferModule());
        injector.getInstance(IController.class).setUpEndpoints();
    }
}
