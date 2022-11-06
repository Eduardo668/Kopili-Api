package br.com.kopili.models;

import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@NoArgsConstructor
@ToString
public class ChatEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    private String person1_username;

    @NotNull
    @Size(max = 200)
    private String person2_username;
    
    @OneToMany(mappedBy = "message_chat")
    private Set<MessageEntity> messages;

    @JsonIgnore
    @ManyToMany
    private List<UserEntity> userChat;




}
