package com.knoldus.common.exceptions;

import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;

public class ServiceUnreachable extends BaseTransportException {

    private static final String DEFAULT_MESSAGE = "The backend color is unavailable or unreachable.";

    public ServiceUnreachable() {
        this(DEFAULT_MESSAGE);
    }

    public ServiceUnreachable(String errorMessage) {
        super(TransportErrorCode.fromHttp(503), errorMessage);
    }

    public ServiceUnreachable(BaseError error) {
        super(TransportErrorCode.fromHttp(503), error);
    }

}
