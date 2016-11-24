package org.jaxrs.messanger.exception;

import org.jaxrs.messanger.models.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;


//@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {
        ErrorMessage errorMessage = new ErrorMessage(
                e.getMessage(),
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                "https://jersey.java.net/documentation/latest/index.html");

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage)
                .build();
    }
}
