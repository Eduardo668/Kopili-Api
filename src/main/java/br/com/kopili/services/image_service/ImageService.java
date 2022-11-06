package br.com.kopili.services.image_service;

import java.util.List;

import br.com.kopili.models.ImageEntity;

public interface ImageService {
    
    public ImageEntity saveImage(ImageEntity newImage);

    public void deleteUserImage(Long image_id);
    public void deletePostImage(Long image_id);

    public List<ImageEntity> findAllImages();

}
