package com.application.fumetti.mappers.data.lookup.books;

import com.application.fumetti.db.lookup.books.Bindings;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record BindingsData(@JsonProperty("id") Long id,
                           @JsonProperty("description") String description) {
    public static BindingsData map(HashMap<String, Object> data) {
        return new BindingsData(Long.valueOf(data.get("id").toString()), data.get("description").toString());
    }

    public static BindingsData map(Bindings map) {
        return new BindingsData(map.id, map.description);
    }
}
