package br.com.api.controllers;

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

    // End-Point para editar um usuario pelo id
    @PutMapping("/edit{user_id}")
    public ResponseEntity<UserEntity> editUser(@RequestBody UserEntity editedUser, @PathVariable Long user_id){
        try{
            return ResponseEntity.ok(userService.editUser(editedUser,user_id));
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    // End-Point para deletar usuario pelo id
    @DeleteMapping("/delete{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long user_id){
        try{
            userService.deleteUser(user_id);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
