package br.com.api.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
public class ImageEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(max = 255)
    private String location;

    @Size(max = 255)
    private String nome;
    
    @OneToOne(mappedBy = "userImage")
    private UserEntity imageUser;
    
    @OneToOne(mappedBy = "image")
    private PostEntity imagePost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "ImageEntity [id=" + id + ", location=" + location + ", nome=" + nome + "]";
    }

    
}
