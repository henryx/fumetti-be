package com.application.fumetti.mappers.responses;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.results.CurrencyResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CurrenciesResponse extends Response<CurrenciesResponse> {
    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CurrencyResult> data;

    @JsonCreator
    public CurrenciesResponse() {
    }

    public CurrenciesResponse(Operations operation, Results result, List<CurrencyResult> data) {
        super(operation, result);
        this.data = data;
    }

    @JsonGetter
    public List<CurrencyResult> getData() {
        return data;
    }
}
