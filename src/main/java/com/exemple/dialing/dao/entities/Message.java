package com.exemple.dialing.dao.entities;

public class Message {
    private int idMsg;
    private String message;
    private User sender;

    public Message() {}

    public Message(String message, User sender) {
        this.message = message;
        this.sender = sender;
    }
    public int getID_MESSAGE() {
        return idMsg;
    }

    public void setID_MESSAGE(int idMsg) {
        this.idMsg = idMsg;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
