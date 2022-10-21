package br.com.api.services.image_service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.api.file_management.post.PostFileSystemRepo;
import br.com.api.file_management.user.UserFileSystemRepo;
import br.com.api.models.ImageEntity;
import br.com.api.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final PostFileSystemRepo postFileSystemRepo;
    private final UserFileSystemRepo userFileSystemRepo;


    public ImageServiceImpl(ImageRepository imageRepository, PostFileSystemRepo postFileSystemRepo, 
    UserFileSystemRepo userFileSystemRepo) {

        this.imageRepository = imageRepository;
        this.postFileSystemRepo = postFileSystemRepo;
        this.userFileSystemRepo = userFileSystemRepo;
    }

    @Override
    public ImageEntity saveImage(ImageEntity newImage) {
        try {
            System.out.println("passou aqui");
            return imageRepository.save(newImage);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao persitir a imagem", e);
        }
    }

    @Override
    public void deleteUserImage(Long image_id) {
        try {
            Optional<ImageEntity> image_data = imageRepository.findById(image_id);
            if (image_data.isEmpty()) {
                throw new RuntimeException("Esta imagem não existe");
            }
            System.out.println(image_data.get());
            userFileSystemRepo.deleteAnImageInTheFileSystem(image_data.get().getLocation()); 
            imageRepository.delete(image_data.get());
            System.out.println("passou depois de tentar deletar a imagem");
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar a imagem", e);
        }

    }

    @Override
    public void deletePostImage(Long image_id) {
        try {
            Optional<ImageEntity> image_data = imageRepository.findById(image_id);
            if (image_data.isEmpty()) {
                throw new RuntimeException("Esta imagem não existe");
            }
            System.out.println(image_data.get());
             
            postFileSystemRepo.deleteAnImageInTheFileSystem(image_data.get().getLocation());
             imageRepository.delete(image_data.get());
            System.out.println("passou depois de tentar deletar a imagem");
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar a imagem", e);
        }

    }

    @Override
    public List<ImageEntity> findAllImages() {
        return imageRepository.findAll();
    }

    
    
}
