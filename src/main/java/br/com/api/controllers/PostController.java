//package br.com.api.controllers;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//import br.com.api.controllers.PostController;
//
//
//
//
//
//public class PostController{
//
//    @GetMapping("/{id}")
//	public ResponseEntity<PostModel> findByIdPost(@PathVariable long id){
//		return repository.findById(id)
//				.map(resp -> ResponseEntity.ok(resp))
//				.orElse(ResponseEntity.notFound().build());
//	}
//
//}