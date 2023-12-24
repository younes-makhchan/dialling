package com.exemple.dialing.dao.entities;

public class Message {
    private String message;
    private User sender;
    private   int ID_MESSAGE;
    public Message() {
    }

    public Message(String message, User sender) {
        this.message = message;
        this.sender = sender;

    }

    public int getID_MESSAGE() {
        return ID_MESSAGE;
    }

    public void setID_MESSAGE(int ID_MESSAGE) {
        this.ID_MESSAGE = ID_MESSAGE;
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
