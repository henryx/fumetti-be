package com.application.fumetti.mappers.data;

import com.application.fumetti.db.Books;
import com.application.fumetti.mappers.data.lookup.books.BindingsData;
import com.application.fumetti.mappers.data.lookup.books.PreservationsData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public record BooksData(@JsonProperty("id") @JsonInclude(JsonInclude.Include.NON_NULL) Long id,
                        @JsonProperty("series") SeriesData series,
                        @JsonProperty("number") Long number,
                        @JsonProperty("published") String published,
                        @JsonProperty("price") BigDecimal price,
                        @JsonProperty("currency") CurrencyData currency,
                        @JsonProperty("binding") BindingsData binding,
                        @JsonProperty("preservation") PreservationsData preservation,
                        @JsonProperty("note") String note
) {
    public BooksData map(HashMap<String, Object> data) {
        var series = (SeriesData) data.get("series");
        var currency = (CurrencyData) data.get("currency");
        var binding = (BindingsData) data.get("binding");
        var preservation = (PreservationsData) data.get("preservation");

        return new BooksData(Long.valueOf(data.get("id").toString()), series,
                Long.valueOf(data.get("number").toString()), data.get("published").toString(),
                new BigDecimal(data.get("price").toString()),
                currency, binding, preservation, data.get("note").toString());
    }

    public BooksData map(Books data) {
        var series = SeriesData.map(data.series);
        var currency = CurrencyData.map(data.currency);
        var binding = BindingsData.map(data.binding);
        var preservation = PreservationsData.map(data.preservation);

        return new BooksData(data.id, series, data.number, data.published.format(DateTimeFormatter.ISO_LOCAL_DATE),
                data.price, currency, binding, preservation, data.note);
    }
}
