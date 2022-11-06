package br.com.kopili.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@ToString
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -6315870526453041934L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    private String fullname;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String username;

    @NotNull
    @Size(max = 100)
    private String password;

    @NotNull
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy") 
    @NotNull
    private Date born;

    @OneToMany(mappedBy = "user_comment")
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonManagedReference
    private Set<CommentEntity> comments;

    @OneToMany(mappedBy = "userPost")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private List<PostEntity> user_posts;

    @ManyToMany(mappedBy = "userChat")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ChatEntity> chat_list;

    //@JsonIgnore
    @ManyToMany(mappedBy = "userFollowed")
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonManagedReference
    private List<FollowerEntity> follow_list;

    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ImageEntity userImage;

    @ManyToMany(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<RoleEntity> roles;


}
