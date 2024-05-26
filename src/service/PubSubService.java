package service;

import db.InMemoryDb;
import model.Message;
import model.Subscriber;
import model.Topic;

import java.util.HashMap;
import java.util.Map;

public class PubSubService {
    InMemoryDb inMemoryDb = InMemoryDb.getInstance();
    Map<String, TopicHandler> topicHandlerMap;

    public PubSubService() {
        this.topicHandlerMap = new HashMap<>();
    }

    public void addTopic(String id) {
        Topic topic = new Topic(id);
        if (inMemoryDb.getTopic(id) == null) {
            inMemoryDb.addTopic(topic);
        } else {
            System.out.println("Topic already exists");
        }
    }

    public void addSubscriber(String id) {
        Subscriber subscriber = new Subscriber(id);
        if (inMemoryDb.getSubscriber(id) == null) {
            inMemoryDb.addSubscriber(subscriber);
        } else {
            System.out.println("Subscriber already exists");
        }
    }

    public void subscribeTopic(String topicId, String subscriberId) {
        if (!inMemoryDb.isSubscriberExist(subscriberId)) {
            System.out.println("Subscriber dont exists");
            return;
        }
        if (!inMemoryDb.isTopicExists(topicId)) {
            System.out.println("Topic dont exists");
            return;
        }
        inMemoryDb.getTopic(topicId).addSubscriber(inMemoryDb.getSubscriber(subscriberId));
    }

    public void publishMessage(String topicId, String message) {
        if (!inMemoryDb.isTopicExists(topicId)) {
            System.out.println("Topic dont exists");
            return;
        }
        Message messageObj = new Message(message);
        if(!topicHandlerMap.containsKey(topicId)){
            TopicHandler topicHandler = new TopicHandler(topicId);
            topicHandlerMap.put(topicId,topicHandler);
        }
        inMemoryDb.getTopic(topicId).addMessage(messageObj);
        topicHandlerMap.get(topicId).publish();
    }

    public void resetOffset(String subscriberId,String topicId){
        if (!inMemoryDb.isTopicExists(topicId)) {
            System.out.println("Topic dont exists");
            return;
        }
        if (!inMemoryDb.isSubscriberExist(subscriberId)) {
            System.out.println("Subscriber dont exists");
            return;
        }

        if(!topicHandlerMap.containsKey(topicId)){
            TopicHandler topicHandler = new TopicHandler(topicId);
            topicHandlerMap.put(topicId,topicHandler);
        }
        topicHandlerMap.get(topicId).resetOffset(subscriberId);


    }
}
