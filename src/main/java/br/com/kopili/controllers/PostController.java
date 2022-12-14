package br.com.kopili.controllers;

import java.util.List;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.kopili.models.PostEntity;
import br.com.kopili.services.post_service.PostService;

@RestController
@RequestMapping("post")
public class PostController{

    @Autowired
    private PostService postService;

    // @GetMapping
	// public ResponseEntity<List<PostEntity>> findAllPost(){
	// 	return ResponseEntity.ok(postRepository.findAll());
	// }

    @GetMapping("/read")
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
    public ResponseEntity<PostEntity> editPost(@RequestBody PostEntity editedPost, @PathVariable("id") Long post_id){
        try{
            return ResponseEntity.ok(postService.editPost(editedPost, post_id));
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/delete/post_id={id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable("id") Long post_id){
        try{
            postService.deletePost(post_id);
            return ResponseEntity.ok(true);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/findPostImage/post_id={post_id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<FileSystemResource> findPostImage(@PathVariable Long post_id){
        try{
            return ResponseEntity.ok(postService.findPostImage(post_id));
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao realizar a requisi????o de encontrar a imagem do usuario",e);
        }
    }


}