package model;

import java.util.*;

public class Topic {
    String id;
    Map<String, Subscriber> subscriberMap;
    List<Message> messages;

    public Topic(String id) {
        this.id = id;
        subscriberMap = new HashMap<>();
        messages = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Map<String, Subscriber> getSubscriberMap(){
        return subscriberMap;
    }

    public synchronized void addMessage(Message message) {
        messages.add(message);
    }

    public int getTotalMessages() {
        return messages.size();
    }

    public Message getMessage(int i) {
        return messages.get(i);
    }

    public void addSubscriber(Subscriber subscriber) {
        subscriberMap.put(subscriber.getId(), subscriber);
    }
}
