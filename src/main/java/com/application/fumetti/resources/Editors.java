package com.application.fumetti.resources;

import com.application.fumetti.db.Nations;
import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.EditorData;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/editors")
public class Editors {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postNation(EditorData req) {
        var nation = new Nations();
        nation.id = req.nation().id();

        var editor = new com.application.fumetti.db.Editors();
        editor.name = req.name();
        editor.hq = req.hq();
        editor.website = req.website();
        editor.nation = nation;
        editor.persist();

        return new Response(Operations.EDITORS, Results.OK);
    }
}
