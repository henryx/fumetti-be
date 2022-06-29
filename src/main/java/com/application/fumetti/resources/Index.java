package com.application.fumetti.resources;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class Index {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Integer> index() {
        var resp = new Response<Integer>(Operations.INDEX, Results.OK);
        resp.setMessage("Fumetti Database Backend");
        return resp;
    }
}