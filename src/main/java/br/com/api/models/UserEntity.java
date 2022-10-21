package br.com.api.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
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
//    @JsonManagedReference
    private Set<CommentEntity> comments;

    @OneToMany(mappedBy = "userPost")
    @JsonManagedReference
    private List<PostEntity> user_posts;

    @ManyToMany(mappedBy = "userChat")
    private List<ChatEntity> chat_list;

    //@JsonIgnore
    @ManyToMany(mappedBy = "userFollowed")
    //@JsonManagedReference
    private List<FollowerEntity> follow_list;

    @OneToOne(cascade = CascadeType.ALL)
    private ImageEntity userImage;
    
    @ManyToMany(mappedBy = "user_roles")
    private List<RoleEntity> roles;

    

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentEntity> comments) {
        this.comments = comments;
    }

    public List<PostEntity> getUser_posts() {
        return user_posts;
    }

    public void setUser_posts(List<PostEntity> user_posts) {
        this.user_posts = user_posts;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

   

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<FollowerEntity> getFollow_list() {
        return follow_list;
    }

    public void setFollow_list(List<FollowerEntity> follow_list) {
        this.follow_list = follow_list;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", born=" + born +
                '}';
    }

    public List<ChatEntity> getChat_list() {
        return chat_list;
    }

    public void setChat_list(List<ChatEntity> chat_list) {
        this.chat_list = chat_list;
    }

    public ImageEntity getUserImage() {
        return userImage;
    }

    public void setUserImage(ImageEntity userImage) {
        this.userImage = userImage;
    }
}
