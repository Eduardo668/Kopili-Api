package br.com.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.models.CommentEntity;
import br.com.api.services.comment_service.CommentService;

@RestController
@RequestMapping("comment")
public class CommentController{

    @Autowired
    private CommentService commentService;

    @GetMapping("/read")
    public ResponseEntity<List<CommentEntity>> findAllComment(){
        return ResponseEntity.ok(commentService.findAllComment());
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createComment(@RequestBody CommentEntity comment){
        try{
            System.out.println(comment);
            commentService.createComment(comment);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            throw new RuntimeException("Erro", e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable("id") Long comment_id){
        try{
            commentService.deleteComment(comment_id);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}