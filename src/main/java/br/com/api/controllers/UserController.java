package br.com.api.controllers;

import br.com.api.models.PostEntity;
import br.com.api.models.ChatEntity;
import br.com.api.models.CommentEntity;
import br.com.api.models.UserEntity;
import br.com.api.services.user_service.UserServiceImpl;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    // End-Point para a criação/cadastro de um usuario
    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody UserEntity user){
        try{
            System.out.println(user);
            userService.createUser(user);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            throw new RuntimeException("Falhou", e);
        }

    }

    // End-Point para visualizar todos os usuarios que estão cadastrados no banco de dados
    @GetMapping("/read")
    public ResponseEntity<List<UserEntity>> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/read/{username}")
    public ResponseEntity<UserEntity> findUser(@PathVariable("username") String username){
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }
    // End-Point para editar um usuario pelo id
    @PutMapping("/edit/{id}")
    public ResponseEntity<UserEntity> editUser(@RequestBody UserEntity editedUser, @PathVariable("id") Long user_id){
        try{
            return ResponseEntity.ok(userService.editUser(editedUser,user_id));
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    // End-Point para deletar usuario pelo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long user_id){
        try{
            userService.deleteUser(user_id);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/makePost/{id}")
    public ResponseEntity<UserEntity> makePost(@RequestBody PostEntity newPost, @PathVariable("id") Long user_id){
        try{
            return ResponseEntity.ok(userService.makePost(user_id, newPost));
        }
        catch (Exception e){
            throw new RuntimeException("RUIM", e);
        }
    }

    @PostMapping("/followSomeone/user1_id={user1_id}/user2_id={user2_id}")
    public ResponseEntity<String> followSomeone(@PathVariable("user1_id") Long user1,
                                             @PathVariable("user2_id") Long user2){
        try {
            userService.follow(user1, user2);
            return ResponseEntity.ok("AMIZADE criada com sucesso");
        }catch (Exception e){
            throw new RuntimeException("Ruim", e);
        }
    }

    @GetMapping("readUserFollowed/{user_id}")
    public ResponseEntity<List<UserEntity>> findAllUserFollowed(@PathVariable("user_id") Long user_id){
        return ResponseEntity.ok(userService.findAllFollowedUsers(user_id));
    }

    @PostMapping("/makeComment/{id}")
    public ResponseEntity<UserEntity> makeComment(@RequestBody CommentEntity newComment, @PathVariable ("id") Long user_id){
        try{
            return ResponseEntity.ok(userService.makeComment(user_id, newComment));
        }
        catch (Exception e){
            throw new RuntimeException("Erro", e);
        }
    }

    @PostMapping("/startChat/user1_id={user1_id}/user2_id={user2_id}")
    public ResponseEntity<ChatEntity> startChat(@PathVariable("user1_id") Long user1_id,
                                      @PathVariable("user2_id") Long user2_id){
            
        try{
            return ResponseEntity.ok(userService.startChat(user1_id, user2_id));
        }
        catch(Exception e){
            throw new RuntimeException("Ocorreu um erro na requisição para criar o chat", e);
        }

    }

    @PostMapping("/sendMessage/chat_id={chat_id}")
    public ResponseEntity<ChatEntity> sendMessage(@RequestBody String message,
                                                  @PathVariable("chat_id") Long chat_id){
        try {
            return ResponseEntity.ok(userService.sendMessage(message, chat_id));
        }
        catch (Exception e){
            throw new RuntimeException("Erro a fazer a requisição para o envio da mensagem");
        }
    }



}
