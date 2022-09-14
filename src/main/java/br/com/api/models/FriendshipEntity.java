package br.com.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.catalina.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
public class FriendshipEntity implements Serializable {

    private static final long serialVersionUID = -6315870426453041934L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long user1;


    @NotNull
    private Long user2;

    @JsonIgnore
    @ManyToMany
//    @JoinTable(
//            name = "friend_user",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "friend_id")
//    )
    //@JsonBackReference
    private Set<UserEntity> userFriend;


//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private UserEntity friend;

//    public UserEntity getFriend() {
//        return friend;
//    }
//
//    public void setFriend(UserEntity friend) {
//        this.friend = friend;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getUserfriend_id() {
//        return userfriend_id;
//    }
//
//    public void setUserfriend_id(Long userfriend_id) {
//        this.userfriend_id = userfriend_id;
//    }

    public Long getUser1() {
        return user1;
    }

    public void setUser1(Long user1) {
        this.user1 = user1;
    }

    public Long getUser2() {
        return user2;
    }

    public void setUser2(Long user2) {
        this.user2 = user2;
    }

    public Set<UserEntity> getUserFriend() {
        return userFriend;
    }

    public void setUserFriend(Set<UserEntity> userFriend) {
        this.userFriend = userFriend;
    }

    @Override
    public String toString() {
        return "FriendshipEntity{" +
                "id=" + id +
                ", user1=" + user1 +
                ", user2=" + user2 +
                '}';
    }
}
