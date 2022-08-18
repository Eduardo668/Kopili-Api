package br.com.api.models;

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
    private Long post_id;

    @NotNull
    @Lob
    private String description;

    @NotNull
    private Date createdAt;

    @OneToMany(mappedBy = "postComments")
    @JsonManagedReference
    private Set<CommentEntity> comments;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private UserEntity post;

    public PostEntity() {

    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
