package com.application.fumetti.resources;

import com.application.fumetti.db.Editors;
import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.CollectionData;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/collections")
public class Collections {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response<CollectionData> postCollections(CollectionData req) {
        Editors editor = Editors.find("id_casa_editrice", req.editor().id()).firstResult();

        var collection = new com.application.fumetti.db.Collections();
        collection.name = req.name();
        collection.editor = editor;
        collection.persist();

        return new Response<>(Operations.COLLECTIONS, Results.OK);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response<CollectionData> getCollections() {
        var data = com.application.fumetti.db.Collections.findAll()
                .stream().map(e -> CollectionData.map((com.application.fumetti.db.Collections) e)).toList();
        var resp = new Response<CollectionData>(Operations.COLLECTIONS, Results.OK);
        resp.setData(data);

        return resp;
    }
}
