package com.knoldus.common.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lightbend.lagom.javadsl.api.deser.ExceptionMessage;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.Singular;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonIgnoreProperties({"name", "detail"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseExceptionMessage extends ExceptionMessage {

    private static final long serialVersionUID = 1L;

    private Integer status;

    @Setter(AccessLevel.NONE)
    @Singular("error")
    private List<BaseError> errors = new ArrayList<>();

    public BaseExceptionMessage(String errorCode, String exceptionName, String errorMessage) {
        super(exceptionName, errorMessage);

        this.addError(BaseError.builder()
                .id(UUID.randomUUID())
                .time(Instant.now().toEpochMilli())
                .errorCode(errorCode)
                .internalMessage(errorMessage)
                .build()
        );
    }

    public BaseExceptionMessage(BaseError error) {
        this(error.getDeveloperMessage(), error);
    }

    public BaseExceptionMessage(String exceptionName, BaseError error) {
        super(exceptionName, error.getInternalMessage());

        this.addError(error);
    }

    public void addError(BaseError error) {
        this.errors.add(error);
    }
}
