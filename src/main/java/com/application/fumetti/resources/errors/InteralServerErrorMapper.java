package com.application.fumetti.resources.errors;

import com.application.fumetti.enums.Operations;
import com.application.fumetti.enums.Results;
import com.application.fumetti.mappers.responses.ErrorResponse;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Provider
public class InteralServerErrorMapper implements ExceptionMapper<InternalServerErrorException> {

    @Context
    public HttpHeaders headers;

    @Override
    public Response toResponse(InternalServerErrorException e) {
        var msg = new ErrorResponse(Operations.ERROR.getOperation(), Results.KO.getResult(), e.getMessage(), null);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(msg.toString()).type(getAcceptType()).build();
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

