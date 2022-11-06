package br.com.kopili.services.user_service.user_delete_strategy;

import org.springframework.stereotype.Service;

import br.com.kopili.models.UserEntity;
import br.com.kopili.services.image_service.ImageServiceImpl;

@Service
public class DeleteUserImage implements UserDeleteStrategy {

    private final ImageServiceImpl imageService;

    public DeleteUserImage(ImageServiceImpl imageService){
        this.imageService = imageService;
    }
    
  
    @Override
    public void verifyAndDeleteIfExist(UserEntity user) {
        if (user.getUserImage() != null){
            imageService.deleteUserImage(user.getUserImage().getId());
        }

    }

}