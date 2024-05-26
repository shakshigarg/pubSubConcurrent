package service;

import db.InMemoryDb;
import model.Message;
import model.Subscriber;

import java.util.concurrent.atomic.AtomicInteger;

public class SubscriberWorker implements Runnable {
    InMemoryDb inMemoryDb = InMemoryDb.getInstance();
    String subscriberId;
    String topicId;
    AtomicInteger offset;

    public SubscriberWorker(String subscriberId, String topicId, AtomicInteger offset) {
        this.subscriberId = subscriberId;
        this.topicId = topicId;
        this.offset = offset;
    }

    @Override
    public void run() {
        Subscriber subscriber = inMemoryDb.getSubscriber(subscriberId);
        synchronized (offset) {
            do {
                int currOffset = offset.get();
                while (currOffset >= inMemoryDb.getTopic(topicId).getTotalMessages()) {
                    try {
                        offset.wait();
                        currOffset = offset.get();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                Message message = inMemoryDb.getTopic(topicId).getMessage(currOffset);
                subscriber.consumeMessage(message);
                offset.compareAndSet(currOffset, currOffset + 1);
            } while (true);
        }
    }

    public synchronized void wakeUpIfNeeded() {
        synchronized (offset) {
            offset.notify();
        }
    }
}
