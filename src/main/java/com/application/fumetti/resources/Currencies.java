package com.application.fumetti.resources;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.CurrencyData;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/currencies")
public class Currencies {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postCurrencies(CurrencyData req) {
        var currencies = new com.application.fumetti.db.Currencies();
        currencies.name = req.name();
        currencies.symbol = req.symbol();
        currencies.valueLire = req.valueLire();
        currencies.valueEuro = req.valueEuro();
        currencies.persist();

        return new Response(Operations.LOOKUP, Results.OK);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response<CurrencyData> getCurrencies() {
        List<com.application.fumetti.db.Currencies> currencies = com.application.fumetti.db.Currencies.findAll().list();

        var data = currencies.stream().map(CurrencyData::map).collect(Collectors.toList());
        var resp = new Response<CurrencyData>(Operations.LOOKUP, Results.OK);
        resp.setData(data);

        return resp;
    }
}
