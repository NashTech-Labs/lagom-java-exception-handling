package com.knoldus.color.module;

import com.knoldus.color.api.ColorService;
import com.knoldus.color.impl.ColorServiceImpl;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class ColorServiceModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(ColorService.class, ColorServiceImpl.class);

    }
}
