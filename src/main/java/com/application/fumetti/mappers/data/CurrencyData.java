package com.application.fumetti.mappers.data;

import com.application.fumetti.db.Currencies;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.HashMap;

public record CurrencyData(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                           @JsonProperty("name") String name,
                           @JsonProperty("symbol") String symbol,
                           @JsonProperty("value_lire") @JsonInclude(JsonInclude.Include.NON_NULL) BigDecimal valueLire,
                           @JsonProperty("value_euro") @JsonInclude(JsonInclude.Include.NON_NULL) BigDecimal valueEuro) {

    public static CurrencyData map(HashMap<String, Object> data) {
        return new CurrencyData(Long.valueOf(data.get("id").toString()), data.get("name").toString(),
                data.get("symbol").toString(),
                new BigDecimal(data.getOrDefault("value_lire", "0").toString()),
                new BigDecimal(data.getOrDefault("value_euro", "0").toString()));
    }

    public static CurrencyData map(Currencies map) {
        return new CurrencyData(map.id, map.name, map.symbol, map.valueLire, map.valueEuro);
    }
}
