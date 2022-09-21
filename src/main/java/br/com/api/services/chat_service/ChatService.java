package br.com.api.services.chat_service;

import br.com.api.models.ChatEntity;

import java.util.List;

public interface ChatService {

    public ChatEntity createChat(ChatEntity newChat);

    public List<ChatEntity> showAllChats();

//    public ChatEntity editChat(Long chat_id, ChatEntity updatedChat);

    public void deleteChat(Long chat_id);

    public ChatEntity findChatById(Long chat_id);


}
