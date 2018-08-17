package com.knoldus.common.exceptions;

import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;

public class ColorCodeUndefined extends BaseTransportException {

    private static final String DEFAULT_MESSAGE = "Color code undefined";


    public ColorCodeUndefined() {
        super(TransportErrorCode.BadRequest, BaseError.builder()
                .internalMessage(DEFAULT_MESSAGE)
                .build()
        );
    }

    public ColorCodeUndefined(TransportErrorCode tec, BaseError error) {
        super(tec, DEFAULT_MESSAGE, error);
    }

    public ColorCodeUndefined(BaseError error) {
        super(TransportErrorCode.BadRequest, error);
    }
}
