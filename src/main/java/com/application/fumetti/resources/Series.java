package com.application.fumetti.resources;

import com.application.fumetti.db.Collections;
import com.application.fumetti.db.lookup.series.Frequency;
import com.application.fumetti.db.lookup.series.Genre;
import com.application.fumetti.db.lookup.series.Status;
import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.SeriesData;
import com.application.fumetti.mappers.data.lookup.series.FrequencyData;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/series")
public class Series {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response<SeriesData> postSeries(SeriesData req) {
        Collections collection = Collections.findById(req.collection().id());
        Frequency frequency = Frequency.find("description", req.frequency().description()).firstResult();
        Status status = Status.find("description", req.status().description()).firstResult();
        Genre genre = Genre.find("description", req.genre().description()).firstResult();

        var series = new com.application.fumetti.db.Series();
        series.name = req.name();
        series.note = req.note();

        series.collection = collection;
        series.frequency = frequency;
        series.status = status;
        series.genre = genre;
        series.persist();

        return new Response<>(Operations.SERIES, Results.OK);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response<SeriesData> getSeries() {
        List<com.application.fumetti.db.Series> currencies = com.application.fumetti.db.Series.findAll().list();

        var data = currencies.stream().map(SeriesData::map).collect(Collectors.toList());
        var resp = new Response<SeriesData>(Operations.SERIES, Results.OK);
        resp.setData(data);

        return resp;
    }

    @GET
    @Path("/frequencies")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<FrequencyData> getSeriesFrequency() {
        List<Frequency> currencies = Frequency.findAll().list();

        var data = currencies.stream().map(FrequencyData::map).collect(Collectors.toList());
        var resp = new Response<FrequencyData>(Operations.LOOKUP, Results.OK);
        resp.setData(data);

        return resp;
    }
}
