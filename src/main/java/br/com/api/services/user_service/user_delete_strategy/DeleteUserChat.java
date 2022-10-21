package br.com.api.services.user_service.user_delete_strategy;

import org.springframework.stereotype.Service;

import br.com.api.models.UserEntity;
import br.com.api.services.chat_service.ChatServiceImpl;

@Service
public class DeleteUserChat implements UserDeleteStrategy {


    private final ChatServiceImpl chatService;

    public DeleteUserChat(ChatServiceImpl chatService){
        this.chatService = chatService;
    }

    @Override
    public void verifyAndDeleteIfExist(UserEntity user) {
        if (!user.getChat_list().isEmpty()){
            user.getChat_list().forEach(chat -> {
                chatService.deleteChat(chat.getId());
            });
        }
    }




}