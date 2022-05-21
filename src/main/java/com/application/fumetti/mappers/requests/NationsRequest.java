package com.application.fumetti.mappers.requests;

import com.application.fumetti.mappers.results.CurrencyResult;
import com.application.fumetti.mappers.results.NationResult;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NationsRequest {
    private final NationResult data;

    public NationsRequest(@JsonProperty("name") String name,
                          @JsonProperty("sign") String sign,
                          @JsonProperty("currency") CurrencyResult currency) {
        this.data = new NationResult(null, name, sign, currency);
    }

    public NationResult getData() {
        return data;
    }
}
