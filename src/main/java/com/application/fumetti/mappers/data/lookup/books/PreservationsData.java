package com.application.fumetti.mappers.data.lookup.books;

import com.application.fumetti.db.lookup.books.Preservations;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record PreservationsData(@JsonProperty("id") Long id,
                                @JsonProperty("description") String description) {
    public static PreservationsData map(HashMap<String, Object> data) {
        return new PreservationsData(Long.valueOf(data.get("id").toString()), data.get("description").toString());
    }

    public static PreservationsData map(Preservations map) {
        return new PreservationsData(map.id, map.description);
    }
}
