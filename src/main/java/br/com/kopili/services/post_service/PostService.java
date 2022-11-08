package br.com.kopili.services.post_service;

import br.com.kopili.models.PostEntity;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

public interface PostService {

   public PostEntity createPost(PostEntity newPost);

   public PostEntity findPostById(Long post_id);

   public List<PostEntity> findAllPost();

   public PostEntity editPost(PostEntity editedPost, Long post_id);

   public void deletePost(Long post_id);

   public FileSystemResource findPostImage(Long post_id);


}
