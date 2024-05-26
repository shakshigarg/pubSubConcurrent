package model;

import java.util.UUID;

public class Message {
    String id;
    String text;

    public Message(String text) {
        this.id= UUID.randomUUID().toString();
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
