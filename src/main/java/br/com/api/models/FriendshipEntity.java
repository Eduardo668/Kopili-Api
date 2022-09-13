package br.com.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class FriendshipEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull
//    private Long user1;
//
//    @NotNull
//    private Long user2;

    @ManyToMany
    @JoinColumn(name = "user_id")
//    @JsonBackReference
    private Set<UserEntity> userFriend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


//    public Long getUser1() {
//        return user1;
//    }
//
//    public void setUser1(Long user1) {
//        this.user1 = user1;
//    }
//
//    public Long getUser2() {
//        return user2;
//    }
//
//    public void setUser2(Long user2) {
//        this.user2 = user2;
//    }

    public Set<UserEntity> getUserFriend() {
        return userFriend;
    }

    public void setUserFriend(Set<UserEntity> userFriend) {
        this.userFriend = userFriend;
    }
}
