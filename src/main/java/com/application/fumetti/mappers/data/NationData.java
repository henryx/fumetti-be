package com.application.fumetti.mappers.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record NationData(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                         @JsonProperty("name") String name,
                         @JsonProperty("sign") String sign,
                         @JsonProperty("currency") CurrencyData currency) {
}