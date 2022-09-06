package br.com.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity(name = "Postagem")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    private String description;

    @Size(max = 100)
    @NotNull
    private String subject;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date date;

    @OneToMany(mappedBy = "postComments")
    
    private Set<CommentEntity> comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    
    private UserEntity post;

    public PostEntity() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }

    public UserEntity getPost() {
        return post;
    }

    public void setPost(UserEntity post) {
        this.post = post;
    }


}
