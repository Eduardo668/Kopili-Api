package br.com.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "Comentario")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @NotNull
    @Lob
    private String content;

    @NotNull
    private Date comment_date;

    @OneToOne(mappedBy = "user_commented")
    private UserEntity user_comments;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    @JsonBackReference
    private PostEntity postComments;

    public CommentEntity() {
    }

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }
}
