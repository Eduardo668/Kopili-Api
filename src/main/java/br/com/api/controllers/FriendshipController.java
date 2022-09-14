package br.com.api.controllers;

import br.com.api.models.FriendshipEntity;
import br.com.api.services.friendship_service.FriendshipServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendshipController {

    public FriendshipController(FriendshipServiceImpl friendshipService) {
        this.friendshipService = friendshipService;
    }

    private final FriendshipServiceImpl friendshipService;

    @GetMapping("read")
    public ResponseEntity<List<FriendshipEntity>> findAllFriendships(){
        return ResponseEntity.ok(friendshipService.findAll());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteFriendship(@PathVariable("id") Long friendship_id){
        try {
            friendshipService.removeFriendship(friendship_id);
            return ResponseEntity.ok("Friendship deletado com sucesso");
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao deletar o user", e);
        }
    }

}
