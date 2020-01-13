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
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

    @Context
    private HttpHeaders headers;

    public Response toResponse(NotFoundException ex) {
        JsonObject msg = Json.createObjectBuilder()
                .add("message", "Not found")
                .add("op", "ko")
                .build();

        return Response.status(404).entity(msg.toString()).type(getAcceptType()).build();
    }

    private String getAcceptType() {
        List<MediaType> accepts = headers.getAcceptableMediaTypes();
        if (accepts != null && accepts.size() > 0) {
            return MediaType.APPLICATION_JSON;
        } else {
            return MediaType.TEXT_PLAIN;
        }
    }
}
