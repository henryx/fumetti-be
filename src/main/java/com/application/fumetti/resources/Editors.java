package com.application.fumetti.resources;

import com.application.fumetti.db.Nations;
import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.EditorData;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/editors")
public class Editors {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postNation(EditorData req) {
        Nations nation = Nations.find("id_nazione", req.nation().id()).firstResult();

        var editor = new com.application.fumetti.db.Editors();
        editor.name = req.name();
        editor.hq = req.hq();
        editor.website = req.website();
        editor.nation = nation;
        editor.persist();

        return new Response(Operations.EDITORS, Results.OK);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response<EditorData> getEditors() {
        List<com.application.fumetti.db.Editors> editors = com.application.fumetti.db.Editors.findAll().list();

        var data = editors.stream().map(EditorData::map).collect(Collectors.toList());

        var resp = new Response<EditorData>(Operations.LOOKUP, Results.OK);
        resp.setData(data);
        return resp;
    }
}
