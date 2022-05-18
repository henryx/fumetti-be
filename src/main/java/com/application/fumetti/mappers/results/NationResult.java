package com.application.fumetti.mappers.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NationResult {
    private final Long id;
    private final String name;
    private final String sign;
    private final CurrencyResult currency;

    public NationResult(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                        @JsonProperty("name") String name,
                        @JsonProperty("sign") String sign,
                        @JsonProperty("currency") CurrencyResult currency) {
        this.id = id;
        this.name = name;
        this.sign = sign;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSign() {
        return sign;
    }

    public CurrencyResult getCurrency() {
        return currency;
    }
}