package br.com.kopili.services.message_service;

import br.com.kopili.models.MessageEntity;

public interface MessageService {

    public MessageEntity createMessage(MessageEntity newMessage);

    public void deleteMessage(Long message_id);

}
