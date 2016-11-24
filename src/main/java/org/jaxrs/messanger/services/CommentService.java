package org.jaxrs.messanger.services;

import org.jaxrs.messanger.database.DataBaseStubs;
import org.jaxrs.messanger.models.Comment;
import org.jaxrs.messanger.models.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CommentService {

    private static Map<Long, Message> messages = DataBaseStubs.getMessages();

    public List<Comment> getAllComments(long messageId) {
        if (messageId <= 0) {
            return null;
        }

        return new ArrayList<>(messages.get(messageId).getComments().values());
    }

    public Comment getComment(long messageId, long commentId) {
        if (messageId <= 0 || commentId <= 0) {
            return null;
        }

        return messages.get(messageId).getComments().get(commentId);
    }

    public Comment addComment(long messageId, Comment comment) {
        if (messageId <= 0 || comment == null) {
            return null;
        }

        Message message = messages.get(messageId);
        comment.setId(message.getComments().size() + 1);
        message.setComment(comment);

        return comment;
    }

    public Comment updateComment(long messageId, long commentId, Comment comment) {
        if (messageId <= 0 || commentId <= 0 || comment == null) {
            return null;
        }

        Message message = messages.get(messageId);
        comment.setId(commentId);
        comment.setCreated(LocalDateTime.now());
        message.setComment(comment);

        return comment;
    }

    public Comment deleteComment(long messageId, long commentId) {
        if (messageId <= 0 || commentId <= 0) {
            return null;
        }

        Message message = messages.get(messageId);
        return message.getComments().remove(messageId);
    }
}
