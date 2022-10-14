package br.com.api.services.image_service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.api.models.ImageEntity;
import br.com.api.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
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
    public void deleteImage(Long image_id) {
        try {
            Optional<ImageEntity> image_data = imageRepository.findById(image_id);
            if (image_data.isEmpty()) {
                throw new RuntimeException("Esta imagem não existe");
            }
            System.out.println(image_data.get());
            imageRepository.delete(image_data.get());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar a imagem", e);
        }

    }

    @Override
    public List<ImageEntity> findAllImages() {
        return imageRepository.findAll();
    }

    
    
}
