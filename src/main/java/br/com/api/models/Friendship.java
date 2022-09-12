package br.com.api.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Repository;

@Entity
public class Friendship {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private UserEntity user1;

    private UserEntity user2;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private UserEntity finaluser1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private UserEntity finaluser2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser1() {
        return user1;
    }

    public void setUser1(UserEntity user1) {
        this.user1 = user1;
    }

    public UserEntity getUser2() {
        return user2;
    }

    public void setUser2(UserEntity user2) {
        this.user2 = user2;
    }

    public UserEntity getFinaluser1() {
        return finaluser1;
    }

    public void setFinaluser1(UserEntity finaluser1) {
        this.finaluser1 = finaluser1;
    }

    public UserEntity getFinaluser2() {
        return finaluser2;
    }

    public void setFinaluser2(UserEntity finaluser2) {
        this.finaluser2 = finaluser2;
    }

 
    

}
