package com.application.fumetti.mappers.data;

import com.application.fumetti.db.Collections;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record CollectionData(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                             @JsonProperty("name") String name,
                             @JsonProperty("editor") EditorData editor) {

    public static CollectionData map(HashMap<String, Object> data) {
        @SuppressWarnings("unchecked") var nestedMap = (HashMap<String, Object>) data.get("editor");
        var nation = EditorData.map(nestedMap);

        return new CollectionData(Long.valueOf(data.get("id").toString()), data.get("name").toString(),
                nation);
    }

    public static CollectionData map(Collections map) {
        var editor = EditorData.map(map.editor);
        return new CollectionData(map.id, map.name, editor);
    }
}
