package service;

import db.InMemoryDb;
import model.Subscriber;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TopicHandler {
    InMemoryDb inMemoryDb = InMemoryDb.getInstance();
    String topicId;
    Map<String, SubscriberWorker> subscriberWorkerMap;

    public TopicHandler(String topicId) {
        this.topicId = topicId;
        subscriberWorkerMap = new HashMap<>();
    }

    public void publish() {
        Map<String, Subscriber> subscriberMap = inMemoryDb.getTopic(topicId).getSubscriberMap();
        for (Subscriber subscriber : subscriberMap.values()) {
            startSubscriberThread(subscriber.getId());
        }
    }

    public void startSubscriberThread(String subscriberId) {
        if (!subscriberWorkerMap.containsKey(subscriberId)) {
            SubscriberWorker subscriberWorker = new SubscriberWorker(subscriberId, topicId, new AtomicInteger(0));
            subscriberWorkerMap.put(subscriberId, subscriberWorker);
            Thread thread=new Thread(subscriberWorkerMap.get(subscriberId));
            thread.setDaemon(true);
            thread.start();
        }
        subscriberWorkerMap.get(subscriberId).wakeUpIfNeeded();
    }


    public void resetOffset(String subscriberId) {
        if (!subscriberWorkerMap.containsKey(subscriberId)) {
            SubscriberWorker subscriberWorker = new SubscriberWorker(subscriberId, topicId, new AtomicInteger(0));
            subscriberWorkerMap.put(subscriberId, subscriberWorker);
            Thread thread=new Thread(subscriberWorkerMap.get(subscriberId));
            thread.setDaemon(true);
            thread.start();
        } else {
            subscriberWorkerMap.get(subscriberId).offset.getAndSet(0);
        }
        subscriberWorkerMap.get(subscriberId).wakeUpIfNeeded();
    }
}
