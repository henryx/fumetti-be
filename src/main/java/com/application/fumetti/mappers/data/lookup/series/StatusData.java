package com.application.fumetti.mappers.data.lookup.series;

import com.application.fumetti.db.lookup.series.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record StatusData(@JsonProperty("id") Long id,
                         @JsonProperty("description") String description) {

    public static StatusData map(HashMap<String, Object> data) {
        return new StatusData(Long.valueOf(data.get("id").toString()), data.get("description").toString());
    }

    public static StatusData map(Status map) {
        return new StatusData(map.id, map.description);
    }
}
