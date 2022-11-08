package br.com.kopili.services.post_service;

import br.com.kopili.file_management.post.PostFileSystemRepo;
import br.com.kopili.models.ImageEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import br.com.kopili.models.PostEntity;
import br.com.kopili.repository.PostRepository;
import br.com.kopili.services.image_service.ImageService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ImageService imageService;
    private final PostFileSystemRepo postFileSystem;





	@Override
    public PostEntity createPost(PostEntity newPost) {
        try {
            Date dataAtual = new Date();

            newPost.setDate(dataAtual);
            return postRepository.save(newPost);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PostEntity> findAllPost() {
        return postRepository.findAll();
    }

	@Override
    public PostEntity editPost(PostEntity editedPost, Long id) {
        try {
            return postRepository.findById(id).map(post -> {
                post.setLink(editedPost.getLink());
                post.setDescription(editedPost.getDescription());
                post.setSubject(editedPost.getSubject());
                post.setImage(editedPost.getImage());
                return postRepository.save(post);
            }).orElseGet(() -> {
                editedPost.setId(id);
                return postRepository.save(editedPost);
            });
        } catch (Exception e){
            throw new RuntimeException("Erro", e);
        }

    }

	@Override
    public void deletePost(Long id) {
        try {
            Optional<PostEntity> post_data = postRepository.findById(id);
            if (post_data.isEmpty()){
                throw new RuntimeException("Este post não existe");
            }

            if (post_data.get().getImage() != null){
                System.out.println("Passou dentro do if para deletar a imagem do post");
                imageService.deletePostImage(post_data.get().getImage().getId());
            }

            postRepository.delete(post_data.get());
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileSystemResource findPostImage(Long post_id) {
        try{
            Optional<PostEntity> post_data = postRepository.findById(post_id);
            if (post_data.isEmpty()){
                throw new RuntimeException("Este post não existe");
            }

            ImageEntity post_image = post_data.get().getImage();
            return postFileSystem.findInPostImages(post_image.getLocation());

        }catch (Exception e){
            throw new RuntimeException("Erro ao retornar a imagem do post", e);
        }

    }

    @Override
    public PostEntity findPostById(Long post_id) {
        Optional<PostEntity> post_data = postRepository.findById(post_id);
        if(post_data.isEmpty()){
            throw new RuntimeException("Este post não existe");
        }
        return post_data.get();
    }

}