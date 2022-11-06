package br.com.kopili.controllers;

import br.com.kopili.models.PostEntity;
import br.com.kopili.models.ChatEntity;
import br.com.kopili.models.CommentEntity;
import br.com.kopili.models.UserEntity;
import br.com.kopili.services.user_service.UserServiceImpl;

import br.com.kopili.utility.AlgorithmUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final AlgorithmUtil algorithmUtil;


    // End-Point para a criação/cadastro de um usuario
    @PostMapping("/create")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user){
        try{
            return ResponseEntity.ok(userService.createUser(user));
        }     catch (Exception e){
            throw new RuntimeException("Falhou", e);
        }
    }

    @PostMapping("/create/admin")
    public ResponseEntity<UserEntity> createUserAdmin(@RequestBody UserEntity newAdmin){
        try{
            return ResponseEntity.ok(userService.createUserAdmin(newAdmin));
        }     catch (Exception e){
            throw new RuntimeException("Falhou", e);
        }
    }


    @PutMapping("/userImageUpload/user_id={user_id}")
    public ResponseEntity<UserEntity> uploadUserImage(@RequestParam MultipartFile image,
    @PathVariable Long user_id){

        try{
            return ResponseEntity.ok(userService.saveUserImage(image.getBytes(), 
            image.getOriginalFilename(), user_id));
        }
        catch(Exception e){
            throw new RuntimeException("Erro na requisição de salvamento da imagem do usuario", e);
        }
      
    }

    @PutMapping("/postImageUpload/user_id={user_id}/post_id={post_id}")
    public ResponseEntity<UserEntity> uploadPostImage(@RequestParam MultipartFile postImage, 
    @PathVariable Long user_id, @PathVariable Long post_id){

        try{
            return ResponseEntity.ok(userService.savePostImageMadeByUser(postImage.getBytes(), 
            postImage.getOriginalFilename(), user_id, post_id));
        }
        catch(Exception e){
            throw new RuntimeException("Erro na requisição de salvamento da imagem do post");
        }


    }



    @GetMapping(value = "/findUserImage/user_id={user_id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<FileSystemResource> findUserImage(@PathVariable Long user_id){
        try{
            return ResponseEntity.ok(userService.findUserImage(user_id));
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao realizar a requisição de encontrar a imagem do usuario",e);
        }
        
    }

    @GetMapping(value = "/findUserPostImage/user_id={user_id}/post_id={post_id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<FileSystemResource> findUserImage(@PathVariable Long user_id, @PathVariable Long post_id){
        try{
            return ResponseEntity.ok(userService.findUserPostImage(post_id, user_id));
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao realizar a requisição de encontrar a imagem do usuario",e);
        }
    }


    // End-Point para visualizar todos os usuarios que estão cadastrados no banco de dados
    @GetMapping("/read")
    public ResponseEntity<List<UserEntity>> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/findAUser/{username}")
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

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        userService.refreshToken(request, response);
    }



}
