package com.application.fumetti.mappers.responses;

import com.application.fumetti.mappers.Mapper;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IndexResponse extends Mapper {
    private final String result;
    private final String operation;

    private final String message;

    public IndexResponse(@JsonProperty("operation") String operation,
                         @JsonProperty("result") String result,
                         @JsonProperty("message") String message) {
        this.operation = operation;
        this.message = message;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getOperation() {
        return operation;
    }
}
