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

            if( newChat.getId() == null) {
                return chatRepository.save(newChat);
            } else {
                throw new RuntimeException("O chat ja existe");
            }
            
            
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
//           Optional<ChatEntity> editedChat = chat_data.map(chat ->{
//                chat.setMessages(updatedChat.getMessages());
//                chat.setPerson1_username(updatedChat.getPerson1_username());
//                chat.setPerson2_username(updatedChat.getPerson2_username());
//                System.out.println(chat);
//               System.out.println(chatRepository.save(chat));
//                return chat;
//            });
//            System.out.println(editedChat.get());
//            return editedChat.get();
//
//
//        }
//        catch (Exception e){
//            throw new RuntimeException("Erro ao editar o chat");
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

    @Override
    public ChatEntity findChatById(Long chat_id) {
        Optional<ChatEntity> chat_data = chatRepository.findById(chat_id);

        if (chat_data.isEmpty()){
            throw new RuntimeException("Esse Chat não existe");
        }

        return chat_data.get();
    }
}
