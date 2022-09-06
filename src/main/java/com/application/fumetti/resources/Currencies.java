package com.application.fumetti.resources;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.CurrencyData;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/currencies")
public class Currencies {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response<CurrencyData> postCurrencies(CurrencyData req) {
        var currencies = new com.application.fumetti.db.Currencies();
        currencies.name = req.name();
        currencies.symbol = req.symbol();
        currencies.valueLire = req.valueLire();
        currencies.valueEuro = req.valueEuro();
        currencies.persist();

        return new Response<>(Operations.LOOKUP, Results.OK);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response<CurrencyData> getCurrencies() {
        var data = com.application.fumetti.db.Currencies.findAll()
                .stream().map(e -> CurrencyData.map((com.application.fumetti.db.Currencies) e)).toList();
        var resp = new Response<CurrencyData>(Operations.LOOKUP, Results.OK);
        resp.setData(data);

        return resp;
    }
}
