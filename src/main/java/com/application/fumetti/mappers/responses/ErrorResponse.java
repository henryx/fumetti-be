package com.application.fumetti.mappers.responses;

import com.application.fumetti.mappers.Mapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class ErrorResponse extends Mapper {

    private final String operation;
    private final String result;
    private final String message;
    private final HashMap<String, String> details;

    public ErrorResponse(@JsonProperty("operation") String operation,
                         @JsonProperty("result") String result,
                         @JsonProperty("message") String message,
                         @JsonProperty("details") HashMap<String, String> details) {
        this.operation = operation;
        this.result = result;
        this.message = message;
        this.details = details;
    }

    public String getOperation() {
        return operation;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, String> getDetails() {
        return details;
    }
}
