package com.message.service;

import com.message.domain.entity.MessageEntity;

import java.util.Collection;

public interface MessageService {

    void sendMessage(String uuid);

    void addMessage(MessageEntity message);

    Collection<MessageEntity> list();

    void update(MessageEntity message);
}
