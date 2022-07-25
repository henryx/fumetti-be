package com.application.fumetti.resources;

import com.application.fumetti.db.lookup.books.Bindings;
import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.lookup.books.BindingsData;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/books")
public class Books {

    @GET
    @Path("/bindings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<BindingsData> getBooksBindings() {
        var data = Bindings.findAll().stream().map(e -> BindingsData.map((Bindings) e)).toList();
        var resp = new Response<BindingsData>(Operations.LOOKUP, Results.OK);
        resp.setData(data);

        return resp;
    }
}
