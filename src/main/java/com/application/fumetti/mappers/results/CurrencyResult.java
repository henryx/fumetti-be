package com.application.fumetti.mappers.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CurrencyResult(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                             @JsonProperty("name") String name,
                             @JsonProperty("symbol") String symbol,
                             @JsonProperty("value_lire") BigDecimal valueLire,
                             @JsonProperty("value_euro") BigDecimal valueEuro) {
}
