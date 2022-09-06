package br.com.api.controllers;

import java.util.List;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.api.controllers.PostController;
import br.com.api.models.PostEntity;
import br.com.api.services.post_service.PostService;

@RestController
public class PostController{

    @Autowired
    private PostService postService;

    // @GetMapping
	// public ResponseEntity<List<PostEntity>> findAllPost(){
	// 	return ResponseEntity.ok(postRepository.findAll());
	// }

    @GetMapping("/list")
    public ResponseEntity<List<PostEntity>> findAllPost(){
        return ResponseEntity.ok(postService.findAllPost());
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> createPost(@RequestBody PostEntity post){
        try{
            System.out.println(post);
            postService.createPost(post);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            throw new RuntimeException("Erro", e);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PostEntity> editPost(@RequestBody PostEntity editedPost, @PathVariable Long post_id){
        try{
            return ResponseEntity.ok(postService.editPost(editedPost, post_id));
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long post_id){
        try{
            postService.deletePost(post_id);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }




}