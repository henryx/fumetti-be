package com.application.fumetti.resources;

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
        return null;
    }
}
