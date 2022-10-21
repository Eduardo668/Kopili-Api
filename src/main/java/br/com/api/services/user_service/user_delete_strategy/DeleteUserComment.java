package br.com.api.services.user_service.user_delete_strategy;

import org.springframework.stereotype.Service;

import br.com.api.models.UserEntity;
import br.com.api.services.comment_service.CommentServiceImpl;

@Service
public class DeleteUserComment implements UserDeleteStrategy {

    private final CommentServiceImpl commentService;

    public DeleteUserComment(CommentServiceImpl commentService){
        this.commentService = commentService;
    }

    @Override
    public void verifyAndDeleteIfExist(UserEntity user) {
        if (!user.getComments().isEmpty()){
            user.getComments().forEach(comment -> {
                commentService.deleteComment(comment.getId());
          
            });
        }
    }

}