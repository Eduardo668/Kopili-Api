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

    @GetMapping("/feed/username={username}")
    public ResponseEntity<List<PostEntity>> feedPage(@PathVariable String username){
        try {
            return ResponseEntity.ok().body(pageService.feedPage(username));
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao realizar a requisição na feedPage");
        }
    }

    @GetMapping("/explorar")
    public ResponseEntity<List<PostEntity>> explorarPage(){
        return ResponseEntity.ok(pageService.explorarPage());
    }

    @GetMapping("/perfil/username={username}")
    public ResponseEntity<UserEntity> perfilPage(@PathVariable String username){
        try{
            return ResponseEntity.ok().body(pageService.perfilPage(username));
        }catch (Exception e){
            throw new RuntimeException("Erro ao realizar a requisição do perfil do usuario",e);
        }
    }




}
