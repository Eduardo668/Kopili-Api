package br.com.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.api.models.ImageEntity;
import br.com.api.services.image_service.ImageServiceImpl;

@RestController
@RequestMapping("/image")
public class ImageController {
    
    private final ImageServiceImpl imageService;

    public ImageController(ImageServiceImpl imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/read")
    public ResponseEntity<List<ImageEntity>> findAllImages(){
        return ResponseEntity.ok(imageService.findAllImages());
    }

    @DeleteMapping("/delete/image_id={image_id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long image_id){
        try{
            imageService.deleteImage(image_id);
            return ResponseEntity.ok("Imagem deletada com sucesso");
        }
        catch(Exception e){
            throw new RuntimeException("Erro ao realizar a requisição de deletação de uma imagem", e);
        }
    }


    

}
