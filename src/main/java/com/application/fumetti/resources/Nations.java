package com.application.fumetti.resources;

import com.application.fumetti.db.Currencies;
import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.requests.NationsRequest;
import com.application.fumetti.mappers.responses.NationsResponse;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/nations")
public class Nations {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public NationsResponse postNation(NationsRequest req) {
        var currency = new Currencies();
        currency.id = req.getCurrency().getId();

        var nation = new com.application.fumetti.db.Nations();
        nation.name = req.getName();
        nation.sign = req.getSign();
        nation.currency = currency;
        nation.persist();

        return new NationsResponse(Operations.LOOKUP, Results.OK, null);
    }
}
