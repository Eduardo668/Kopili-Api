package br.com.kopili.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@ToString
public class FollowerEntity implements Serializable {

    private static final long serialVersionUID = -6315870426453041934L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long user1;


    @JsonIgnore
    @ManyToMany
    private List<UserEntity> userFollowed;

}
