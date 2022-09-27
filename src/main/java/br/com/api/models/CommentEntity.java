package br.com.api.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.Set;

@Entity(name = "Comentario")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 300)
    private String content;

    @NotNull
    private Date comment_date;

    @Size(max = 200)
    @NotNull
    private String user_commented;

    @JsonIgnore
    @ManyToOne
//    @JsonBackReference
    private  UserEntity user_comment;

    @JsonIgnore
    @ManyToMany
    private Set<PostEntity> postComments;

    public CommentEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserEntity getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(UserEntity user_comment) {
        this.user_comment = user_comment;
    }

    public Set<PostEntity> getPostComments() {
        return postComments;
    }

    public String getUser_commented() {
        return user_commented;
    }

    public void setUser_commented(String user_commented) {
        this.user_commented = user_commented;
    }

    public void setPostComments(Set<PostEntity> postComments) {
        this.postComments = postComments;
    }
}