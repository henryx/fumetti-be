package com.application.fumetti.mappers;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Response<T> extends Mapper {
    @JsonProperty("operation")
    private String operation;

    @JsonProperty("result")
    private String result;

    @JsonProperty("message")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonCreator
    public Response() {
    }

    public Response(Operations operation, Results result) {
        this.operation = operation.getOperation();
        this.result = result.getResult();
    }

    public String getOperation() {
        return operation;
    }

    public T setOperation(String operation) {
        this.operation = operation;

        return (T) this;
    }

    public String getResult() {
        return result;
    }

    public T setResult(String result) {
        this.result = result;

        return (T) this;
    }

    public String getMessage() {
        return message;
    }

    public T setMessage(String message) {
        this.message = message;

        return (T) this;
    }
}