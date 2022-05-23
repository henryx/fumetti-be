package com.application.fumetti.mappers.requests;

import com.application.fumetti.mappers.data.CurrencyData;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CurrenciesRequest {
    private final CurrencyData data;

    public CurrenciesRequest(@JsonProperty("name") String name,
                             @JsonProperty("symbol") String symbol,
                             @JsonProperty("value_lire") BigDecimal valueLire,
                             @JsonProperty("value_euro") BigDecimal valueEuro) {
        this.data = new CurrencyData(null, name, symbol, valueLire, valueEuro);
    }

    public CurrencyData getData() {
        return data;
    }
}
