package br.com.api.services.post_service;

import br.com.api.models.PostEntity;

import java.util.List;

public interface PostService {

    public PostEntity createPost(PostEntity newPost);

    public List<PostEntity> readPost();




}
