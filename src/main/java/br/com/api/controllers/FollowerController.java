package br.com.api.controllers;

import br.com.api.models.FollowerEntity;
import br.com.api.services.follower_service.FollowerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FollowerController {

    public FollowerController(FollowerServiceImpl friendshipService) {
        this.friendshipService = friendshipService;
    }

    private final FollowerServiceImpl friendshipService;

    @GetMapping("read")
    public ResponseEntity<List<FollowerEntity>> findAllFriendships(){
        return ResponseEntity.ok(friendshipService.findAll());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteFriendship(@PathVariable("id") Long friendship_id){
        try {
            friendshipService.removeFollow(friendship_id);
            return ResponseEntity.ok("Friendship deletado com sucesso");
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao deletar o user", e);
        }
    }

}
