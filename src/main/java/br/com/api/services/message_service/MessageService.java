package br.com.api.services.message_service;

import br.com.api.models.MessageEntity;

public interface MessageService {

    public MessageEntity createMessage(MessageEntity newMessage);

    public void deleteMessage(Long message_id);

}
