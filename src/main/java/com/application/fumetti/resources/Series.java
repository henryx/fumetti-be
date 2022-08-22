package com.application.fumetti.resources;

import com.application.fumetti.db.Editors;
import com.application.fumetti.db.lookup.series.Frequency;
import com.application.fumetti.db.lookup.series.Genre;
import com.application.fumetti.db.lookup.series.Status;
import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.SeriesData;
import com.application.fumetti.mappers.data.lookup.series.FrequencyData;
import com.application.fumetti.mappers.data.lookup.series.GenreData;
import com.application.fumetti.mappers.data.lookup.series.StatusData;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/series")
public class Series {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response<SeriesData> postSeries(SeriesData req) {
        Editors editor = Editors.find("nome", req.editor().name()).firstResult();
        Frequency frequency = Frequency.find("description", req.frequency().description()).firstResult();
        Status status = Status.find("description", req.status().description()).firstResult();
        Genre genre = Genre.find("description", req.genre().description()).firstResult();

        var series = new com.application.fumetti.db.Series();
        series.name = req.name();
        series.note = req.note();

        series.editor = editor;
        series.frequency = frequency;
        series.status = status;
        series.genre = genre;
        series.persist();

        return new Response<>(Operations.SERIES, Results.OK);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response<SeriesData> getSeries() {
        var data = com.application.fumetti.db.Series.findAll()
                .stream().map(e -> SeriesData.map((com.application.fumetti.db.Series) e)).toList();
        var resp = new Response<SeriesData>(Operations.SERIES, Results.OK);
        resp.setData(data);

        return resp;
    }

    @GET
    @Path("/frequencies")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<FrequencyData> getSeriesFrequency() {
        var data = Frequency.findAll().stream().map(e -> FrequencyData.map((Frequency) e)).toList();
        var resp = new Response<FrequencyData>(Operations.LOOKUP, Results.OK);
        resp.setData(data);

        return resp;
    }

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<StatusData> getSeriesStatus() {
        var data = Status.findAll().stream().map(e -> StatusData.map((Status) e)).toList();
        var resp = new Response<StatusData>(Operations.LOOKUP, Results.OK);
        resp.setData(data);

        return resp;
    }

    @GET
    @Path("/genre")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<GenreData> getSeriesGenre() {
        var data = Genre.findAll().stream().map(e -> GenreData.map((Genre) e)).toList();
        var resp = new Response<GenreData>(Operations.LOOKUP, Results.OK);
        resp.setData(data);

        return resp;
    }
}
