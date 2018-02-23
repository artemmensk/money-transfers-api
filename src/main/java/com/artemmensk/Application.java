package com.artemmensk;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Application {
    public static void main( String[] args ) {
        Injector injector = Guice.createInjector(new TransferModule());
        IController controller = injector.getInstance(IController.class);
        controller.init();
    }
}
