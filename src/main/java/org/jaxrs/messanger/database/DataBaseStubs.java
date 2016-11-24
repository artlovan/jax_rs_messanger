package org.jaxrs.messanger.database;

import org.jaxrs.messanger.models.Comment;
import org.jaxrs.messanger.models.Message;
import org.jaxrs.messanger.models.Profile;

import java.util.HashMap;
import java.util.Map;


public class DataBaseStubs {

    private static Map<Long, Message> messages = new HashMap<>();
    private static Map<Long, Comment> comments = new HashMap<>();
    private static Map<String, Profile> profiles = new HashMap<>();

    static {
        Message m1 = new Message(1L, "Hello World", "pro");
        m1.setComment(new Comment(1L, "comment 1 to message 1", "pro"));
        m1.setComment(new Comment(2L, "comment 2 to message 1", "pro"));
        messages.put(1L, m1);

        Message m2 = new Message(2L, "Hallo Jersey", "pro");
        m2.setComment(new Comment(1L, "comment 1 to message 2", "pro"));
        m2.setComment(new Comment(2L, "comment 2 to message 2", "pro"));
        messages.put(2L, m2);
    }

    static {
        profiles.put("profile1", new Profile(1L, "profile1", "Dan", "Smidth"));
        profiles.put("profile2", new Profile(2L, "profile2", "Phil", "Long"));
    }

    public static Map<Long, Message> getMessages() {
        return messages;
    }

    public static Map<String, Profile> getProfiles() {
        return profiles;
    }

    public static Map<Long, Comment> getComments() {
        return comments;
    }
}
