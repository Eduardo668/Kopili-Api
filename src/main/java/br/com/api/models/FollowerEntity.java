package br.com.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
public class FollowerEntity implements Serializable {

    private static final long serialVersionUID = -6315870426453041934L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long user1;


    @JsonIgnore
    @ManyToMany
    private Set<UserEntity> userFriend;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getUser1() {
        return user1;
    }

    public void setUser1(Long user1) {
        this.user1 = user1;
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
//                ", user2=" + user2 +
                '}';
    }
}
