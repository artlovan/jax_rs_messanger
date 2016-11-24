package org.jaxrs.messanger.exception;

import org.jaxrs.messanger.models.ErrorMessage;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class WebApplicationExceptionBuilder {

    public static WebApplicationException getWebApplicationException(String errorMessage,
                                                                     Status errorCode,
                                                                     String documentation) {
        Response response = prepareResponse(errorMessage, errorCode, documentation);

        return new WebApplicationException(response);
    }

    public static WebApplicationException getNotFoundException(String errorMessage,
                                                               Status errorCode,
                                                               String documentation) {
        Response response = prepareResponse(errorMessage, errorCode, documentation);

        return new NotFoundException(response);
    }

    private static Response prepareResponse(String errorMessage, Status errorCode, String documentation) {
        ErrorMessage em = new ErrorMessage(errorMessage, errorCode.getStatusCode(), documentation);

        return Response
                    .status(errorCode)
                    .entity(em)
                    .build();
    }

}
