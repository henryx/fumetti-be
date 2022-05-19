package com.application.fumetti.mappers.responses;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.results.NationResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NationsResponse extends Response<NationsResponse> {
    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<NationResult> data;

    @JsonCreator
    public NationsResponse() {
    }

    public NationsResponse(Operations operation, Results result, List<NationResult> data) {
        super(operation, result);
        this.data = data;
    }

    @JsonGetter
    public List<NationResult> getData() {
        return data;
    }
}
