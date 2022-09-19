package br.com.api.services.comment_service;

import org.springframework.stereotype.Service;

import br.com.api.models.CommentEntity;
import br.com.api.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

// import java.util.Date;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
       this.commentRepository = commentRepository;
    }

	@Override
    public CommentEntity createComment(CommentEntity newComment) {
        try {
             Date dataAtual = new Date();
             newComment.setComment_date(dataAtual);
            return commentRepository.save(newComment);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CommentEntity> findAllComment() {
        return commentRepository.findAll();
    }

	@Override
    public void deleteComment(Long id) {
        try {
            Optional<CommentEntity> comment_data = commentRepository.findById(id);

            commentRepository.delete(comment_data.get());
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}