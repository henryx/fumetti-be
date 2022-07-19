package com.application.fumetti.mappers.data;

import com.application.fumetti.db.Series;
import com.application.fumetti.mappers.data.lookup.series.GenreData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record SeriesData(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                         @JsonProperty("name") String name,
                         @JsonProperty("collection") CollectionData collection,
                         @JsonProperty("genre") GenreData genre,
                         @JsonProperty("frequency") String frequency,
                         @JsonProperty("status") String status,
                         @JsonProperty("note") String note
) {

    public static SeriesData map(HashMap<String, Object> data) {
        @SuppressWarnings("unchecked") var nestedCollection = (HashMap<String, Object>) data.get("collection");
        @SuppressWarnings("unchecked") var nestedGenre = (HashMap<String, Object>) data.get("genre");


        var collection = CollectionData.map(nestedCollection);
        var genre = GenreData.map(nestedGenre);

        return new SeriesData(Long.valueOf(data.get("id").toString()), data.get("name").toString(),
                collection, genre, data.get("frequency").toString(),
                data.get("status").toString(), data.get("note").toString());
    }

    public static SeriesData map(Series map) {
        var collection = CollectionData.map(map.collection);
        var genre = GenreData.map(map.genre);
        return new SeriesData(map.id, map.name, collection, genre, map.frequency.description,
                map.status.description, map.note);
    }
}
