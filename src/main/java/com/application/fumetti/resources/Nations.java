package com.application.fumetti.resources;

import com.application.fumetti.db.Currencies;
import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.requests.NationsRequest;
import com.application.fumetti.mappers.responses.NationsResponse;
import com.application.fumetti.mappers.results.CurrencyResult;
import com.application.fumetti.mappers.results.NationResult;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/nations")
public class Nations {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public NationsResponse postNation(NationsRequest req) {
        var currency = new Currencies();
        currency.id = req.getCurrency().id();

        var nation = new com.application.fumetti.db.Nations();
        nation.name = req.getName();
        nation.sign = req.getSign();
        nation.currency = currency;
        nation.persist();

        return new NationsResponse(Operations.LOOKUP, Results.OK, null);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public NationsResponse getNations() {
        List<com.application.fumetti.db.Nations> currencies = com.application.fumetti.db.Nations.findAll().list();

        var data = currencies.stream().map(ie -> {
            var currency = new CurrencyResult(ie.currency.id, ie.currency.name,
                    ie.currency.symbol, ie.currency.valueLire, ie.currency.valueEuro);
            return new NationResult(ie.id, ie.name, ie.sign, currency);
        }).collect(Collectors.toList());

        return new NationsResponse(Operations.LOOKUP, Results.OK, data);
    }
}
