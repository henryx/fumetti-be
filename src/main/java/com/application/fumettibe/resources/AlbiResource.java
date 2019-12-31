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

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
    public Response postJson() {
        JsonObject res = Json.createObjectBuilder()
                .add("msg", "Not implemented")
                .add("op", "ok")
                .build();

        return Response.ok(res).build();
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
