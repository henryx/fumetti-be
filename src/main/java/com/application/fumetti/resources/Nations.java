package com.application.fumetti.resources;

import com.application.fumetti.db.Currencies;
import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.NationData;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/nations")
public class Nations {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response<NationData> postNation(NationData req) {
        Currencies currency = Currencies.find("id_valuta", req.currency().id()).firstResult();

        var nation = new com.application.fumetti.db.Nations();
        nation.name = req.name();
        nation.sign = req.sign();
        nation.currency = currency;
        nation.persist();

        return new Response<>(Operations.LOOKUP, Results.OK);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response<NationData> getNations() {
        var data = com.application.fumetti.db.Nations.findAll()
                .stream().map(e -> NationData.map((com.application.fumetti.db.Nations) e)).toList();
        var resp = new Response<NationData>(Operations.LOOKUP, Results.OK);
        resp.setData(data);

        return resp;
    }
}
