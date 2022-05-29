package com.application.fumetti.resources;

import com.application.fumetti.db.Currencies;
import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.NationData;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/nations")
public class Nations {
    @Inject
    ObjectMapper mapper;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postNation(NationData req) {
        Currencies currency = Currencies.find("id_valuta", req.currency().id()).firstResult();

        var nation = new com.application.fumetti.db.Nations();
        nation.name = req.name();
        nation.sign = req.sign();
        nation.currency = currency;
        nation.persist();

        return new Response(Operations.LOOKUP, Results.OK);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response<NationData> getNations() {
        List<com.application.fumetti.db.Nations> currencies = com.application.fumetti.db.Nations.findAll().list();

        var data = currencies.stream().map(NationData::map).collect(Collectors.toList());

        var resp = new Response<NationData>(Operations.LOOKUP, Results.OK);
        resp.setData(data);
        return resp;
    }
}
