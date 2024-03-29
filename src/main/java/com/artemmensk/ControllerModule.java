package com.artemmensk;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ControllerModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(IController.class).to(Controller.class).in(Singleton.class);
    }
}
