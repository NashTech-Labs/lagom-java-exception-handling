package com.example.service.module;

import com.example.service.api.ColorService;
import com.google.inject.AbstractModule;
import com.example.service.impl.ColorServiceImpl;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class ColorServiceModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(ColorService.class, ColorServiceImpl.class);
    }
}
