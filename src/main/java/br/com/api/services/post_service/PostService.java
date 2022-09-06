package br.com.api.services.post_service;

import br.com.api.models.PostEntity;

import java.util.List;

public interface PostService {

   public PostEntity createPost(PostEntity newPost);

   public List<PostEntity> findAllPost();

   public PostEntity editPost(PostEntity editedPost, Long post_id);

   public PostEntity deletePost(Long post_id);





}
