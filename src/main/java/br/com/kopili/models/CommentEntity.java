package br.com.kopili.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Entity(name = "Comentario")
@Data
@NoArgsConstructor
@ToString
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
    private Set<PostEntity> post_comments;


}