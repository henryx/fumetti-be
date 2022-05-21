package com.application.fumetti.mappers.requests;

import com.application.fumetti.mappers.results.CurrencyResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.math.BigDecimal;

public class CurrenciesRequest {
    @JsonUnwrapped
    private final CurrencyResult data;

    public CurrenciesRequest(@JsonProperty("name") String name,
                             @JsonProperty("symbol") String symbol,
                             @JsonProperty("value_lire") BigDecimal valueLire,
                             @JsonProperty("value_euro") BigDecimal valueEuro) {
        this.data = new CurrencyResult(null, name, symbol, valueLire, valueEuro);
    }

    public CurrencyResult getData() {
        return data;
    }
}
