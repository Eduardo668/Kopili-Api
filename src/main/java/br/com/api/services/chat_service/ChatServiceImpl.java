package br.com.api.services.chat_service;

import br.com.api.models.ChatEntity;
import br.com.api.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public ChatEntity createChat(ChatEntity newChat) {
        try {
            Optional<ChatEntity> chat_data = chatRepository.findById(newChat.getId());
            if(chat_data.isPresent()){
                throw new RuntimeException("Este chat ja existe");
            }
            return chatRepository.save(newChat);
        }
        catch (Exception e){
            throw new RuntimeException("Ocorreu algum erro ao criar o chat", e);
        }
    }

    @Override
    public List<ChatEntity> showAllChats() {
        return chatRepository.findAll();
    }

//    @Override
//    public ChatEntity editChat(Long chat_id, ChatEntity updatedChat) {
//        try {
//            Optional<ChatEntity> chat_data = chatRepository.findById(chat_id);
//            if (chat_data.isEmpty()){
//                throw new RuntimeException("Este chat não existe");
//            }
//            chat_data.map(chat ->{
//                chat.se
//            })
//
//        }
//    }

    @Override
    public void deleteChat(Long chat_id) {
        try{
            Optional<ChatEntity> chat_data = chatRepository.findById(chat_id);
            if (chat_data.isEmpty()){
                throw new RuntimeException("Esse chat não existe");
            }
            chatRepository.delete(chat_data.get());
        }
        catch (Exception e){
            throw new RuntimeException("Ocorreu um erro ao deletar o chat",e);
        }
    }
}
