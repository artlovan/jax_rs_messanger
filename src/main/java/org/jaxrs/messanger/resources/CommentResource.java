package org.jaxrs.messanger.resources;


import org.jaxrs.messanger.models.Comment;
import org.jaxrs.messanger.exception.DataNotFoundException;
import org.jaxrs.messanger.services.CommentService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;


@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CommentResource {

    private CommentService commentService = new CommentService();

    @GET
    public List<Comment> getAllComments(@PathParam("messageId") long messageId) {
        return commentService.getAllComments(messageId);
    }

    @GET
    @Path("/{commentId}")
    public Comment getComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
        Comment comment = commentService.getComment(messageId, commentId);

        if (comment == null) {
            throw new DataNotFoundException(
                    "Message with id " + messageId + " and comment id " + commentId + " not found");
        }

        return commentService.getComment(messageId, commentId);
    }

    @POST
    public Response createComment(@PathParam("messageId") long messageId, Comment comment, @Context UriInfo uriInfo) {
        Comment message = commentService.addComment(messageId, comment);

        String idString = String.valueOf(comment.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(idString).build();

        return Response.created(uri).entity(message).build();
    }

    @PUT
    @Path("/{commentId}")
    public Response updateComment(@PathParam("messageId") long messageId,
                                 @PathParam("commentId") long commentId,
                                 Comment comment) {
        Comment updatedComment = commentService.updateComment(messageId, commentId, comment);

        return Response.accepted(updatedComment).build();
    }

    @DELETE
    @Path("/{commentId}")
    public Response deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
        Comment deletedComment = commentService.deleteComment(messageId, commentId);

        if (deletedComment == null) {
            throw new DataNotFoundException(
                    "Message with id " + messageId + " and comment id " + commentId + " not found");
        }

        return Response.noContent().build();
    }
}
