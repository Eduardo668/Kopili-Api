package br.com.api.controllers;

import br.com.api.models.PostEntity;
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

}
