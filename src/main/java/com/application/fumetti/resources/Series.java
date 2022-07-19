package com.application.fumetti.resources;

import com.application.fumetti.db.Collections;
import com.application.fumetti.db.SeriesFrequency;
import com.application.fumetti.db.SeriesGenre;
import com.application.fumetti.db.SeriesStatus;
import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.SeriesData;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/series")
public class Series {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response<SeriesData> postSeries(SeriesData req) {
        Collections collection = Collections.findById(req.collection().id());
        SeriesFrequency frequency = SeriesFrequency.find("description", req.frequency()).firstResult();
        SeriesStatus status = SeriesStatus.find("description", req.status()).firstResult();
        SeriesGenre genre = SeriesGenre.find("description", req.genre()).firstResult();

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
}
