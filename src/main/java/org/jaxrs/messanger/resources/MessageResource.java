package org.jaxrs.messanger.resources;

import org.jaxrs.messanger.exception.DataNotFoundException;
import org.jaxrs.messanger.models.Message;
import org.jaxrs.messanger.resources.Beans.MessageFilterBean;
import org.jaxrs.messanger.services.MessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;


@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MessageResource {

    private MessageService messageService = new MessageService();

    @GET
    public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
        if (filterBean.getYear() > 0) {
            return messageService.getAllMessagesForYear(filterBean.getYear());
        }

        if (filterBean.getStart() > 0 && filterBean.getSize() > 0) {
            return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
        }

        return messageService.getAllMessages();
    }

    @GET
    @Path(value = "/{id}")
    public Message getMessage(@PathParam("id") long id, @Context UriInfo uriInfo) {
        Message message = messageService.getMessage(id);

        if (message == null) {
            throw new DataNotFoundException("Message with id " + id + " not found");
        }

        message.addLink(getUriForSelf(uriInfo, message), "self");
        message.addLink(getUriForProfile(uriInfo, message), "profile");
        message.addLink(getUriForComments(uriInfo, message), "comments");

        return message;
    }

    @PUT
    @Path(value = "/{id}")
    public Message updateMessage(@PathParam("id") long id, Message message) {
        message.setId(id);
        return messageService.updateMessage(message);
    }

    @POST
    public Response createMessage(Message message, @Context UriInfo uriInfo) {
        Message createdMessage = messageService.addMessage(message);

        String idString = String.valueOf(createdMessage.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(idString).build();

        return Response.created(uri)
                .entity(message)
                .build();
    }

    @DELETE
    @Path(value = "/{id}")
    public void deleteMessage(@PathParam("id") long id) {
        messageService.deleteMessage(id);
    }

    @Path("/{messageId}/comments")
    public CommentResource getCommentResource() {
        return new CommentResource();
    }

    private String getUriForSelf(UriInfo uriInfo, Message message) {
        return uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(Long.toString(message.getId()))
                .build()
                .toString();
    }


    private String getUriForProfile(UriInfo uriInfo, Message message) {
        return uriInfo.getBaseUriBuilder()
                .path(ProfileResource.class)
                .path(message.getAuthor())
                .build()
                .toString();
    }


    private String getUriForComments(UriInfo uriInfo, Message message) {
        return uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(MessageResource.class, "getCommentResource")
                .path(CommentResource.class)
                .resolveTemplate("messageId", message.getId())
                .build()
                .toString();
    }
}
