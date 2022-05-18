package com.application.fumetti.resources.errors;

import com.application.fumetti.enums.Results;
import com.application.fumetti.exceptions.KoException;
import com.application.fumetti.mappers.responses.ErrorResponse;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class KoExceptionMapper implements ExceptionMapper<KoException> {

    @Context
    public HttpHeaders headers;

    @Override
    public Response toResponse(KoException e) {
        var msg = new ErrorResponse(e.getOperation(), Results.KO.getResult(), e.getMessage(), e.getDetails());
        return Response.status(e.getStatusCode()).entity(msg.toString()).type(getAcceptType()).build();
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

