package com.application.fumetti.mappers.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CurrencyResult {
    private final Integer id;
    private final String name;
    private final String symbol;
    private final BigDecimal valueLire;
    private final BigDecimal valueEuro;

    public CurrencyResult(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Integer id,
                          @JsonProperty("name") String name,
                          @JsonProperty("symbol") String symbol,
                          @JsonProperty("value_lire") BigDecimal valueLire,
                          @JsonProperty("value_euro") BigDecimal valueEuro
    ) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.valueLire = valueLire;
        this.valueEuro = valueEuro;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getValueLire() {
        return valueLire;
    }

    public BigDecimal getValueEuro() {
        return valueEuro;
    }
}
