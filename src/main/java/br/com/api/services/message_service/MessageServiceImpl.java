package br.com.api.services.message_service;

import br.com.api.models.MessageEntity;
import br.com.api.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    private final MessageRepository messageRepository;

    @Override
    public MessageEntity createMessage(MessageEntity newMessage) {
        try {
            return messageRepository.save(newMessage);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao criar a messagem", e);
        }
    }

    @Override
    public void deleteMessage(Long message_id) {
         try {
             Optional<MessageEntity> message_data = messageRepository.findById(message_id);
             if (message_data.isEmpty()){
                 throw new RuntimeException("Esta message não existe");
             }
             messageRepository.delete(message_data.get());
         }
         catch (Exception e){
             throw new RuntimeException("Erro ao deletar a message", e);
         }
    }
}
