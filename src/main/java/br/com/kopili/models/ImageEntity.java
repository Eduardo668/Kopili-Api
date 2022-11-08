package br.com.kopili.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@ToString
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

    @JsonIgnore
    @OneToOne(mappedBy = "image")
    private PostEntity imagePost;


}
