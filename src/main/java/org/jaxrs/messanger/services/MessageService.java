package org.jaxrs.messanger.services;

import org.jaxrs.messanger.models.Message;
import org.jaxrs.messanger.database.DataBaseStubs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;


public class MessageService {

    private static Map<Long, Message> messages = DataBaseStubs.getMessages();

    public List<Message> getAllMessages() {
        return new ArrayList<>(messages.values());
    }

    public List<Message> getAllMessagesForYear(Integer year) {
        return messages.values().stream()
                .filter(d -> d.getCreated().getYear() == year)
                .collect(toList());
    }

    public List<Message> getAllMessagesPaginated (Integer start, Integer size) {
        return messages.values().stream()
                .skip(start-1)
                .limit(size-1)
                .collect(toList());
    }

    public Message getMessage(long id) {
        return messages.get(id);
    }

    public Message addMessage(Message message) {
        message.setId(messages.size() + 1);
        messages.put(message.getId(), message);

        return message;
    }

    public Message updateMessage(Message message) {
        if (message.getId() <= 0) {
            return null;
        }

        messages.put(message.getId(), message);

        return message;
    }

    public Message deleteMessage(long id) {
        return messages.remove(id);
    }
}
