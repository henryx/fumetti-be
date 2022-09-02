package com.application.fumetti.resources;

import com.application.fumetti.db.Currencies;
import com.application.fumetti.db.Series;
import com.application.fumetti.db.lookup.books.Bindings;
import com.application.fumetti.db.lookup.books.Preservations;
import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.BooksData;
import com.application.fumetti.mappers.data.lookup.books.BindingsData;
import com.application.fumetti.mappers.data.lookup.books.PreservationsData;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Path("/books")
public class Books {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response<BooksData> postBooks(BooksData req) {
        Series series = Series.findById(req.series().id());
        Currencies currency = Currencies.findById(req.currency().id());
        Bindings binding = Bindings.findById(req.binding().id());
        Preservations preservation = Preservations.findById(req.preservation().id());

        var book = new com.application.fumetti.db.Books();
        book.series = series;
        book.number = req.number();
        book.published = LocalDate.parse(req.published(), DateTimeFormatter.ISO_LOCAL_DATE);
        book.price = req.price();
        book.currency = currency;
        book.binding = binding;
        book.preservation = preservation;
        book.note = req.note();
        book.persist();

        return new Response<>(Operations.BOOKS, Results.OK);
    }

    @GET
    @Path("/bindings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<BindingsData> getBooksBindings() {
        var data = Bindings.findAll().stream().map(e -> BindingsData.map((Bindings) e)).toList();
        var resp = new Response<BindingsData>(Operations.LOOKUP, Results.OK);
        resp.setData(data);

        return resp;
    }

    @GET
    @Path("/preservations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response<PreservationsData> getBooksPreservations() {
        var data = Preservations.findAll().stream().map(e -> PreservationsData.map((Preservations) e)).toList();
        var resp = new Response<PreservationsData>(Operations.LOOKUP, Results.OK);
        resp.setData(data);

        return resp;
    }
}
