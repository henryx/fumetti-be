package com.application.fumetti.mappers.data.lookup.series;

import com.application.fumetti.db.lookup.series.Frequency;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record FrequencyData(@JsonProperty("id") Long id,
                            @JsonProperty("description") String description) {

    public static FrequencyData map(HashMap<String, Object> data) {
        return new FrequencyData(Long.valueOf(data.get("id").toString()), data.get("description").toString());
    }

    public static FrequencyData map(Frequency map) {
        return new FrequencyData(map.id, map.description);
    }
}
