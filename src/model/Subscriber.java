package model;

import service.ISubscriber;

import java.util.UUID;

public class Subscriber implements ISubscriber {
    String id;

    public String getId() {
        return id;
    }

    public Subscriber(String id) {
        this.id= id;
    }

    public void consumeMessage(Message message) {
        //System.out.println(this.id+ " is consuming "+message.text);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(this.id+ " has consumed "+message.text);
    }
}
