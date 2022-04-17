package com.application.fumetti.resources;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.responses.IndexResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class Index {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public IndexResponse index() {
        return new IndexResponse(Operations.INDEX.getOperation(), Results.OK.getResult(), "Fumetti Database Backend");
    }
}