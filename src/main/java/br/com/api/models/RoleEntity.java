package br.com.api.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;


@Entity
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    private String nome;

    @ManyToMany
    private List<UserEntity> user_roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
    public List<UserEntity> getUser_roles() {
        return user_roles;
    }

    public void setUser_roles(List<UserEntity> user_roles) {
        this.user_roles = user_roles;
    }   
    
    @Override
    public String toString() {
        return "RoleEntity [id=" + id + ", nome=" + nome + "]";
    }
}
