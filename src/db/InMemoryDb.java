package db;

import model.Subscriber;
import model.Topic;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDb {
    private static InMemoryDb inMemoryDb = new InMemoryDb();
    Map<String, Subscriber> subscriberMap;
    Map<String, Topic> topicMap;

    private InMemoryDb() {
        subscriberMap = new HashMap<>();
        topicMap = new HashMap<>();
    }

    public static InMemoryDb getInstance() {
        return inMemoryDb;
    }

    public Subscriber getSubscriber(String id) {
        return subscriberMap.get(id);
    }

    public void addSubscriber(Subscriber subscriber) {
        subscriberMap.put(subscriber.getId(), subscriber);
    }

    public Topic getTopic(String id) {
        return topicMap.get(id);
    }

    public void addTopic(Topic topic) {
        topicMap.put(topic.getId(), topic);
    }

    public boolean isSubscriberExist(String id){
        return subscriberMap.containsKey(id);
    }

    public boolean isTopicExists(String id){
        return topicMap.containsKey(id);
    }
}
