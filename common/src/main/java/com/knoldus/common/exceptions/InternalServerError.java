package com.knoldus.common.exceptions;

import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;

public class InternalServerError extends BaseTransportException {

    private static final String DEFAULT_MESSAGE = "An unexpected error occurred.";

    public InternalServerError() {
        this(DEFAULT_MESSAGE);
    }

    public InternalServerError(Throwable throwable) {
        super(TransportErrorCode.fromHttp(500), throwable);
    }

    public InternalServerError(String errorMessage) {
        super(TransportErrorCode.fromHttp(500), errorMessage);
    }

    public InternalServerError(BaseError error) {
        super(TransportErrorCode.fromHttp(500), error);
    }

}
