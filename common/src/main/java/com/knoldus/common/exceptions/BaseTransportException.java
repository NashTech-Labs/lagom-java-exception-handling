package com.knoldus.common.exceptions;

import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import com.lightbend.lagom.javadsl.api.transport.TransportException;

import java.util.List;

public class BaseTransportException extends TransportException {

    public static final BaseTransportException GENERIC_500 = new BaseTransportException(
            TransportErrorCode.fromHttp(500), "An unexpected failure was encountered.");

    private final BaseExceptionMessage mem;

    public BaseTransportException(TransportErrorCode tec, String errorMessage) {
        this(tec, new BaseExceptionMessage(String.valueOf(tec.http()), tec.description(), errorMessage));
    }

    public BaseTransportException(TransportErrorCode tec, BaseError error) {
        this(tec, new BaseExceptionMessage(error));
    }

    public BaseTransportException(TransportErrorCode tec, String exceptionName, BaseError error) {
        this(tec, new BaseExceptionMessage(exceptionName, error));
    }

    public BaseTransportException(TransportErrorCode tec, BaseExceptionMessage mem) {
        super(tec, mem);

        if (tec == null) {
            throw new IllegalArgumentException("A TransportErrorCode is required.");
        }

        this.mem = mem;
        this.setStatus(tec.http());
    }

    public BaseTransportException(TransportErrorCode tec, Throwable throwable) {
        super(tec, throwable);

        if (tec == null) {
            throw new IllegalArgumentException("A TransportErrorCode is required.");
        }

        this.mem = new BaseExceptionMessage(String.valueOf(tec.http()), tec.description(),
                throwable == null ? null : throwable.getMessage());
        this.setStatus(tec.http());
    }

    @Override
    public BaseExceptionMessage exceptionMessage() {
        return mem;
    }

    public Integer getStatus() {
        return mem.getStatus();
    }

    public void setStatus(Integer status) {
        mem.setStatus(status);
    }

    public List<BaseError> getErrors() {
        return mem.getErrors();
    }

    public void addError(BaseError error) {
        mem.addError(error);
    }
}
