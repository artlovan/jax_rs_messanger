package org.jaxrs.messanger.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

    @GET
    @Path("/annotations")
    public String getParamUsingAnnotations(@MatrixParam("param") String matrixParam,
                                           @HeaderParam("customHeader") String customHeader,
                                           @CookieParam("cookie") String cookie) {

        // Riches -> http://localhost:8080/webapi/messages;param=myName
        if (cookie == null) {
            cookie = "new cookie";
        }

        return "matrixParam =>  " + matrixParam + " customHeader => " + customHeader + " cookie name => " + cookie;
    }

    @GET
    @Path("/context")
    public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders) {

        String path = uriInfo.getAbsolutePath().toString();
        String cookies = httpHeaders.getCookies().toString();

        return "Path : " + path + " cookies : " + cookies;
    }
}
