package com.exemple.dialing.service;

import com.exemple.dialing.dao.entities.Message;

import java.util.List;

public interface IMessageService {

    public void addMessage(Message c);
    public void deleteMessageBy(Integer id);
    public List<Message> getAllMessages();
    public void updateMessage(Message c);


}
