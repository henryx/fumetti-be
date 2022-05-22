package com.application.fumetti.mappers;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Response<T> extends Mapper {
    @JsonProperty("operation")
    private String operation;

    @JsonProperty("result")
    private String result;

    @JsonProperty("message")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> data;

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

    public Response setOperation(String operation) {
        this.operation = operation;

        return this;
    }

    public String getResult() {
        return result;
    }

    public Response setResult(String result) {
        this.result = result;

        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;

        return this;
    }

    public Response setData(List<T> data) {
        this.data = data;

        return this;
    }

    public List<T> getData() {
        return data;
    }
}