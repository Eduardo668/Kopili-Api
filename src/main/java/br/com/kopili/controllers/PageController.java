package br.com.kopili.controllers;

import br.com.kopili.models.PostEntity;
import br.com.kopili.models.UserEntity;
import br.com.kopili.services.page_service.PageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/page")
@RequiredArgsConstructor
public class PageController {

    private final PageServiceImpl pageService;

    @GetMapping("/feed/user_id={user_id}")
    public ResponseEntity<List<PostEntity>> feedPage(@PathVariable Long user_id){
        try {
            return ResponseEntity.ok().body(pageService.feedPage(user_id));
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao realizar a requisição na feedPage");
        }
    }

    @GetMapping("/explorar")
    public ResponseEntity<List<PostEntity>> explorarPage(){
        return  null;
    }

    @GetMapping("/perfil/user_id={user_id}")
    public ResponseEntity<UserEntity> perfilPage(@PathVariable Long user_id){
        return null;
    }




}
