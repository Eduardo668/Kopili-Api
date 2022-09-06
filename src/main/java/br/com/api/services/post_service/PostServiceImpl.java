package br.com.api.services.post_service;

import org.springframework.stereotype.Service;
import br.com.api.models.PostEntity;
import br.com.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
            return postRepository.findById(_id).map(post -> {
                post.setDescription(editedPost.getDescription());
                post.setAge(editedPost.getAge());
                post.setPassword(editedPost.getPassword());
                post.setUsername(editedPost.getUsername());
                post.setPhoto(editedPost.getPhoto());
                return postRepository.save(user);
            }).orElseGet(() -> {
                editedUser.setId(id);
                return userRepository.save(editedUser);
            });
        } catch (Exception e){
            throw new RuntimeException("Deu Ruim", e);
        }

    }

	@Override
	public PostEntity deletePost(PostEntity deletePost) {
		// TODO Auto-generated method stub
		return null;
	}

}