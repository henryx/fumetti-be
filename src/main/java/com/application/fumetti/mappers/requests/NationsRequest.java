package com.application.fumetti.mappers.requests;

import com.application.fumetti.mappers.results.CurrencyResult;
import com.application.fumetti.mappers.data.NationData;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NationsRequest {
    private final NationData data;

    public NationsRequest(@JsonProperty("name") String name,
                          @JsonProperty("sign") String sign,
                          @JsonProperty("currency") CurrencyResult currency) {
        this.data = new NationData(null, name, sign, currency);
    }

    public NationData getData() {
        return data;
    }
}
