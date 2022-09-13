package br.com.api.controllers;

import br.com.api.models.FriendshipEntity;
import br.com.api.services.friendship_service.FriendshipServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
