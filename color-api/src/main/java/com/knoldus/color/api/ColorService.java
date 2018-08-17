package com.knoldus.color.api;

import akka.NotUsed;
import com.knoldus.common.model.ColorData;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import static com.lightbend.lagom.javadsl.api.Service.named;

public interface ColorService extends Service {

    ServiceCall<NotUsed, ColorData> read(String color);

    @Override
    default Descriptor descriptor() {
        return named("color").withCalls(
                Service.restCall(Method.GET, "/read?color", this::read)
        ).withAutoAcl(true);
    }
}
