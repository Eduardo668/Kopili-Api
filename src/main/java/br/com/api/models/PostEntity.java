package br.com.api.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;
import java.util.Set;

@Entity(name = "Postagem")
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

    public PostEntity() {

    }

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public ImageEntity getImage() {
		return image;
	}
	public void setImage(ImageEntity image) {
		this.image = image;
	}

	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getDate() {
		return post_date;
	}
	public void setDate(Date date) {
		this.post_date = date;
	}

	public Set<CommentEntity> getComments() {
		return comments;
	}
	public void setComments(Set<CommentEntity> comments) {
		this.comments = comments;
	}

	public UserEntity getUserPost() {
		return userPost;
	}

	public void setUserPost(UserEntity userPost) {
		this.userPost = userPost;
	}

	@Override
    public String toString() {
        return "PostEntity{" +
				"id=" + id +
				"link="+ link + 
				"description="+ description +
				"subject="+ subject+
           
                '}';
    }
	

}
