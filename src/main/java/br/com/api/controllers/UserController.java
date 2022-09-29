package br.com.api.controllers;

import br.com.api.file_management.user.UserImageUploadResponse;
import br.com.api.file_management.user.UserImagesUploadUtil;
import br.com.api.models.PostEntity;
import br.com.api.models.ChatEntity;
import br.com.api.models.CommentEntity;
import br.com.api.models.UserEntity;
import br.com.api.services.user_service.UserServiceImpl;


import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    // End-Point para a criação/cadastro de um usuario
    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody UserEntity user,
                                              @RequestParam("file") MultipartFile multipartFile){
        try{
            System.out.println(user);
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            long size = multipartFile.getSize();

            String fileCode = UserImagesUploadUtil.saveFile(fileName,multipartFile);

            user.setPhoto(fileCode);
            System.out.println(fileCode);

            userService.createUser(user);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            throw new RuntimeException("Falhou", e);
        }

    }

    @PostMapping("/createUserPhoto")
    public ResponseEntity<UserEntity> createUserPhoto(){
        
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

    @PostMapping("/makePost/user_id={user_id}")
    public ResponseEntity<UserEntity> makePost(@RequestBody PostEntity newPost, @PathVariable("user_id") Long user_id){
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

    @PostMapping("/makeComment/user_id={user_id}/post_id={post_id}")
    public ResponseEntity<PostEntity> makeComment(@RequestBody CommentEntity newComment,
                         @PathVariable ("user_id") Long user_id, @PathVariable("post_id") Long post_id){
        try{
            return ResponseEntity.ok(userService.makeComment(user_id,post_id, newComment));
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

    @PostMapping("/saveUserImage")
    public ResponseEntity<String> saveUserImage(@RequestParam("file") MultipartFile multipartFile)
            throws IOException {

        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            long size = multipartFile.getSize();

            System.out.println(fileName);

            String fileCode = UserImagesUploadUtil.saveFile(fileName,multipartFile);

            System.out.println(fileCode);

            return ResponseEntity.ok("YES");

        }
        catch (Exception e){
            throw new RuntimeException("ERRO", e);
        }

    }



}
