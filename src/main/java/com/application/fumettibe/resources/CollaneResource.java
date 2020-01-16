package com.application.fumettibe.resources;

import com.application.fumettibe.db.resources.DbCollane;

import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

@Path("/collane")
public class CollaneResource {

    @Context
    private UriInfo context;

    public CollaneResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        Response res;
        ArrayList<JsonObject> results;

        try (DbCollane db = new DbCollane()) {
            results = db.select();
            res = Response.ok(results).build();
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
        return res;    }
}
