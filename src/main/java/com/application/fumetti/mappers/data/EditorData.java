package com.application.fumetti.mappers.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record EditorData(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                         @JsonProperty("name") String name,
                         @JsonProperty("hq") String hq,
                         @JsonProperty("website") String website,
                         @JsonProperty("nation") NationData nation) {
}