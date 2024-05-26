package service;

import model.Message;

public interface ISubscriber {
    public String getId();
    public void consumeMessage(Message message) ;
}
