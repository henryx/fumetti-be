/*
 * Copyright (C) 2019 Enrico Bianchi <enrico.bianchi@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.application.fumettibe.resources;

import com.application.fumettibe.db.resources.DbAlbi;

import javax.json.Json;
import javax.json.JsonObject;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.SQLException;
import java.text.ParseException;

@Path("/albi")
public class AlbiResource {

    @Context
    private UriInfo context;

    public AlbiResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        JsonObject res = Json.createObjectBuilder()
                .add("msg", "Not implemented")
                .add("op", "ok")
                .build();

        return Response.ok(res).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{serie}")
    public Response getJson(@PathParam("serie") String serie) {
        JsonObject res = Json.createObjectBuilder()
                .add("msg", "Not implemented")
                .add("arg", serie)
                .add("op", "ok")
                .build();

        return Response.ok(res).build();
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
            }
        }

        try (DbAlbi db = new DbAlbi()) {
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
                strmsg = "Cannot insert albo";
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

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{serie}")
    public Response delJSon(@PathParam("serie") String serie) {
        JsonObject res = Json.createObjectBuilder()
                .add("msg", "Not implemented")
                .add("op", "ok")
                .build();

        return Response.ok(res).build();
    }
}
