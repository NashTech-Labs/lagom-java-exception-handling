package com.knoldus.common.exceptions;

import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;

public class ParameterRequired extends BaseTransportException {

    private static final String DEFAULT_MESSAGE = "Parameter Required";

    public ParameterRequired() {
        super(TransportErrorCode.BadRequest, BaseError.builder()
                .internalMessage(DEFAULT_MESSAGE)
                .build()
        );
    }

    public ParameterRequired(Throwable throwable) {
        super(TransportErrorCode.BadRequest, BaseError.builder()
                .internalMessage(DEFAULT_MESSAGE)
                .developerMessage(throwable.getMessage())
                .build()
        );
    }

    public ParameterRequired(BaseError error) {
        super(TransportErrorCode.BadRequest, error);
    }

}
