package com.knoldus.common.exceptions;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseError implements Serializable {

    private static final long serialVersionUID = 1L;

    private String developerMessage;

    private String userMessage;

    private String internalMessage;

    private String errorTitle;

    private String errorCode;

    private String moreInfo;

    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Builder.Default
    private Long time = System.currentTimeMillis();

}
