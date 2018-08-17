package com.knoldus.common.exceptions;

import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;

public class DataNotLoaded extends BaseTransportException {

    public static final String DEFAULT_MESSAGE = "Server data is not loaded.";

    public DataNotLoaded() {
        this(DEFAULT_MESSAGE);
    }

    public DataNotLoaded(String errorMessage) {
        super(TransportErrorCode.InternalServerError, errorMessage);
    }
}
