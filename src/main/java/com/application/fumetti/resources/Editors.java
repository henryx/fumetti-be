package com.application.fumetti.resources;

import com.application.fumetti.db.Nations;
import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.CurrencyData;
import com.application.fumetti.mappers.data.EditorData;
import com.application.fumetti.mappers.data.NationData;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response<EditorData> getEditors() {
        List<com.application.fumetti.db.Editors> editors = com.application.fumetti.db.Editors.findAll().list();

        var data = editors.stream().map(ie -> {
            var currency = new CurrencyData(ie.nation.currency.id, ie.nation.currency.name,
                    ie.nation.currency.symbol, ie.nation.currency.valueLire, ie.nation.currency.valueEuro);
            var nation = new NationData(ie.nation.id, ie.nation.name, ie.nation.sign, currency);
            return new EditorData(ie.id, ie.name, ie.hq, ie.website, nation);
        }).collect(Collectors.toList());

        var resp = new Response<EditorData>(Operations.LOOKUP, Results.OK);
        resp.setData(data);
        return resp;
    }
}
