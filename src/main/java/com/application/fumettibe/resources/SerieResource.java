package com.application.fumettibe.resources;

import com.application.fumettibe.db.resources.DbAlbi;
import com.application.fumettibe.db.resources.DbSerie;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.text.ParseException;

@Path("/serie")
public class SerieResource {

    @Context
    private UriInfo context;

    public SerieResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        Response res;
        JsonArray results;

        try (DbSerie db = new DbSerie()) {
            results = db.select();

            var msg = Json.createObjectBuilder()
                    .add("op", "ok")
                    .add("data", results)
                    .build();

            res = Response.ok(msg).build();
        } catch (Exception e) {
            String strmsg;
            JsonObject msg;

            if (e instanceof NamingException) {
                strmsg = "Database initialization error";
            } else if (e instanceof SQLException) {
                strmsg = "Database connection";
            } else if (e instanceof ParseException) {
                strmsg = "Parsed data error";
            } else {
                strmsg = "Generic error";
            }

            msg = Json.createObjectBuilder()
                    .add("msg", strmsg)
                    .add("op", "ko")
                    .add("motivation", e.getMessage())
                    .build();

            res = Response.status(400).entity(msg).build();
        }
        return res;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(JsonObject data) {
        JsonObject msg;
        Response resp;

        if (data.containsKey("type")) {
            String val = data.getJsonString("type").getString();
            if (val.equals("test")) {
                msg = Json.createObjectBuilder()
                        .add("msg", "POST request")
                        .add("value", val)
                        .add("op", "ok")
                        .build();
                resp = Response.ok(msg).build();
                return resp;
            }
        }

        try (DbSerie db = new DbSerie()) {
            db.insert(data);
            msg = Json.createObjectBuilder()
                    .add("op", "ok")
                    .add("msg", "Albo inserted")
                    .build();
            resp = Response.ok(msg).build();
        } catch (Exception e) {
            String strmsg;

            if (e instanceof NamingException) {
                strmsg = "Database initialization error";
            } else if (e instanceof SQLException) {
                strmsg = "Cannot insert serie";
            } else if (e instanceof ParseException) {
                strmsg = "Parsed data error";
            } else {
                strmsg = "Generic error";
            }

            msg = Json.createObjectBuilder()
                    .add("msg", strmsg)
                    .add("op", "ko")
                    .add("motivation", e.getMessage())
                    .build();

            resp = Response.status(400).entity(msg).build();
        }

        return resp;
    }

}
