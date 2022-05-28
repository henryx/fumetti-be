package com.application.fumetti.mappers.data;

import com.application.fumetti.db.Nations;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record NationData(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                         @JsonProperty("name") String name,
                         @JsonProperty("sign") String sign,
                         @JsonProperty("currency") CurrencyData currency) {

    public static NationData map(HashMap<String, Object> data) {
        var nestedMap = (HashMap<String, Object>) data.get("currency");
        var currency = CurrencyData.map(nestedMap);

        return new NationData(Long.getLong(data.get("id").toString()), data.get("name").toString(),
                data.get("sign").toString(), currency);
    }

    public static NationData map(Nations map) {
        var currency = CurrencyData.map(map.currency);
        return new NationData(map.id, map.name, map.sign, currency);
    }
}