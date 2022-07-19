package com.application.fumetti.mappers.data;

import com.application.fumetti.db.Series;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record SeriesData(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                         @JsonProperty("name") String name,
                         @JsonProperty("collection") CollectionData collection,
                         @JsonProperty("genre") String genre,
                         @JsonProperty("frequency") String frequency,
                         @JsonProperty("status") String status,
                         @JsonProperty("note") String note
) {

    public static SeriesData map(HashMap<String, Object> data) {
        @SuppressWarnings("unchecked") var nestedMap = (HashMap<String, Object>) data.get("collection");
        var collection = CollectionData.map(nestedMap);

        return new SeriesData(Long.valueOf(data.get("id").toString()), data.get("name").toString(),
                collection, data.get("genre").toString(), data.get("frequency").toString(),
                data.get("status").toString(), data.get("note").toString());
    }

    public static SeriesData map(Series map) {
        var collection = CollectionData.map(map.collection);
        return new SeriesData(map.id, map.name, collection, map.genre.description, map.frequency.description,
                map.status.description, map.note);
    }
}
