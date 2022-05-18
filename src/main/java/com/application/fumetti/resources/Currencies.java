package com.application.fumetti.resources;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.requests.CurrenciesRequest;
import com.application.fumetti.mappers.responses.CurrenciesResponse;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/currencies")
public class Currencies {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public CurrenciesResponse postCurrencies(CurrenciesRequest req) {
        var currencies = new com.application.fumetti.db.Currencies();
        currencies.name = req.getName();
        currencies.symbol = req.getSymbol();
        currencies.valueLire = req.getValueLire();
        currencies.valueEuro = req.getValueEuro();
        currencies.persist();

        return new CurrenciesResponse(Operations.LOOKUP, Results.OK, null);
    }
}
