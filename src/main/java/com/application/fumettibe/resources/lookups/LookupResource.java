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

package com.application.fumettibe.resources.lookups;

import com.application.fumettibe.db.resources.lookups.Lookup;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.text.ParseException;

public abstract class LookupResource {
    protected final Lookup lookup;

    public LookupResource(Lookup lookup) {
        this.lookup = lookup;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        Response res;
        JsonArray results;

        try (lookup) {
            results = lookup.select();

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
}
