package com.application.fumetti.mappers.data;

import com.application.fumetti.db.Editors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public record EditorData(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                         @JsonProperty("name") String name,
                         @JsonProperty("hq") String hq,
                         @JsonProperty("website") String website,
                         @JsonProperty("nation") NationData nation) {

    public static EditorData map(HashMap<String, Object> data) {
        var nestedMap = (HashMap<String, Object>) data.get("nation");
        var nation = NationData.map(nestedMap);

        return new EditorData(Long.getLong(data.get("id").toString()), data.get("name").toString(),
                data.get("hq").toString(), data.get("website").toString(), nation);
    }

    public static EditorData map(Editors map) {
        var nation = NationData.map(map.nation);
        return new EditorData(map.id, map.name, map.hq, map.website, nation);
    }
}