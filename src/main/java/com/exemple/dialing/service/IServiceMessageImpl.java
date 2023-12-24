package com.exemple.dialing.service;

import com.exemple.dialing.dao.MessageDao;
import com.exemple.dialing.dao.entities.Message;

import java.util.List;

public class IServiceMessageImpl implements IMessageService {
    MessageDao messageDao;
    public IServiceMessageImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }
    @Override
    public void addMessage(Message c) {
        messageDao.save(c);
    }
    @Override
    public void deleteMessageBy(Integer id) {
    messageDao.deleteById(id);
    }
    @Override
    public List<Message> getAllMessages() {
        return messageDao.getAll();
    }
    @Override
    public void updateMessage(Message c) {
            messageDao.update(c);
    }
}
