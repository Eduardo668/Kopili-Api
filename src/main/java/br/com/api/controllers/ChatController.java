package br.com.api.controllers;

import br.com.api.models.ChatEntity;
import br.com.api.services.chat_service.ChatServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chat")
public class ChatController {

    public ChatController(ChatServiceImpl chatService) {
        this.chatService = chatService;
    }

    private final ChatServiceImpl chatService;

    @GetMapping("/read")
    public ResponseEntity<List<ChatEntity>> readAllChats(){
        return ResponseEntity.ok(chatService.showAllChats());
    }

    @DeleteMapping("/deleteChat/chat_id={chat_id}")
    public ResponseEntity<Boolean> deleteChat(@PathVariable("chat_id") Long chat_id){

        try {
            chatService.deleteChat(chat_id);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao fazer a requisição para deletar o chat");
        }

    }
   

}
