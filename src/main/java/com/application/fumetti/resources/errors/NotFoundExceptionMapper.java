package com.application.fumetti.resources.errors;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Context
    public HttpHeaders headers;

    public Response toResponse(NotFoundException e) {
        var msg = new com.application.fumetti.mappers.Response<>(Operations.ERROR, Results.KO);
        msg.setMessage(e.getMessage());

        return Response.status(Response.Status.NOT_FOUND).entity(msg.toString()).type(getAcceptType()).build();
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
