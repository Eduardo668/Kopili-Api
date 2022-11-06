package br.com.kopili.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;
import java.util.Set;

@Entity(name = "Postagem")
@Data
@NoArgsConstructor
@ToString
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 500)
    private String link;

    @NotNull
    @Size(max = 300)
    private String description;

    @Size(max = 100)
    @NotNull
    private String subject;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy") 
	@NotNull
    private Date post_date;

    @ManyToMany(mappedBy = "post_comments")
    private Set<CommentEntity> comments;

    @ManyToOne
    @JoinColumn(name = "user_id")
	@JsonBackReference
	private UserEntity userPost;
	
	@OneToOne(cascade = CascadeType.ALL)
	private ImageEntity image;


    public void setDate(Date dataAtual) {
        this.post_date = dataAtual;
    }
}
