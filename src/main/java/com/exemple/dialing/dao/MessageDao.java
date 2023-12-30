package com.exemple.dialing.dao;

import com.exemple.dialing.dao.entities.Message;

import java.util.List;

public interface MessageDao extends Dao<Message,Integer>{
    public List<Message> filterSenderAndReceiver(int idSender, int idReceiver);
}
