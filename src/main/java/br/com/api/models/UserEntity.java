package br.com.api.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String email;

    @Size(max = 200)
    private String photo;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy") 
    @NotNull
    private Date born;

    @OneToMany(mappedBy = "user_comment") 
//    @JsonManagedReference
    private Set<CommentEntity> comments;

    @OneToMany(mappedBy = "userPost")
    @JsonManagedReference
    private Set<PostEntity> user_posts;


    //@JsonIgnore
    @ManyToMany(mappedBy = "userFriend")
    //@JsonManagedReference
    private List<FriendshipEntity> friends_list;

    public List<FriendshipEntity> getFriends_list() {
        return friends_list;
    }

    public void setFriends_list(List<FriendshipEntity> friends_list) {
        this.friends_list = friends_list;
    }

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

    public Set<PostEntity> getUser_posts() {
        return user_posts;
    }

    public void setUser_posts(Set<PostEntity> user_posts) {
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", fullname='" + fullname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", photo='" + photo + '\'' +
                ", born=" + born +
                '}';
    }
}
