package br.com.kopili.services.comment_service;

import java.util.List;

import br.com.kopili.models.CommentEntity;


public interface CommentService {

    public CommentEntity createComment(CommentEntity newComment);

    public List<CommentEntity> findAllComment();

    public void deleteComment(Long comment_id);

}