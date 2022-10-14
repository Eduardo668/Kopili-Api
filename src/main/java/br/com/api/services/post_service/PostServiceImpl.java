package br.com.api.services.post_service;

import org.springframework.stereotype.Service;
import br.com.api.models.PostEntity;
import br.com.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
       this.postRepository = postRepository;
    }

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

            postRepository.delete(post_data.get());
        }
        catch (Exception e){
            throw new RuntimeException(e);
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