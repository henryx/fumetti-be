package com.application.fumetti.mappers.data;

import com.application.fumetti.db.Series;
import com.application.fumetti.mappers.data.lookup.series.FrequencyData;
import com.application.fumetti.mappers.data.lookup.series.GenreData;
import com.application.fumetti.mappers.data.lookup.series.StatusData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record SeriesData(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                         @JsonProperty("name") String name,
                         @JsonProperty("genre") GenreData genre,
                         @JsonProperty("editor") EditorData editor,
                         @JsonProperty("frequency") FrequencyData frequency,
                         @JsonProperty("status") StatusData status,
                         @JsonProperty("note") String note
) {

    public static SeriesData map(HashMap<String, Object> data) {
        @SuppressWarnings("unchecked") var nestedGenre = (HashMap<String, Object>) data.get("genre");
        @SuppressWarnings("unchecked") var nestedFrequency = (HashMap<String, Object>) data.get("frequency");
        @SuppressWarnings("unchecked") var nestedStatus = (HashMap<String, Object>) data.get("status");
        @SuppressWarnings("unchecked") var nestedEditor = (HashMap<String, Object>) data.get("editor");


        var genre = GenreData.map(nestedGenre);
        var frequency = FrequencyData.map(nestedFrequency);
        var status = StatusData.map(nestedStatus);
        var editor = EditorData.map(nestedEditor);

        return new SeriesData(Long.valueOf(data.get("id").toString()), data.get("name").toString(),
                genre, editor, frequency, status, data.get("note").toString());
    }

    public static SeriesData map(Series map) {
        var genre = GenreData.map(map.genre);
        var frequency = FrequencyData.map(map.frequency);
        var status = StatusData.map(map.status);
        var editor = EditorData.map(map.editor);

        return new SeriesData(map.id, map.name, genre, editor, frequency, status, map.note);
    }
}
