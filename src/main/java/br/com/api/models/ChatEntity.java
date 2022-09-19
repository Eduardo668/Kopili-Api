package br.com.api.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerson1_username() {
        return person1_username;
    }

    public void setPerson1_username(String person1_username) {
        this.person1_username = person1_username;
    }

    public String getPerson2_username() {
        return person2_username;
    }

    public void setPerson2_username(String person2_username) {
        this.person2_username = person2_username;
    }

    public Set<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(Set<MessageEntity> messages) {
        this.messages = messages;
    }
}
