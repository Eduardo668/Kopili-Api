package br.com.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 500)
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private ChatEntity message_chat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChatEntity getMessage_chat() {
        return message_chat;
    }

    public void setMessage_chat(ChatEntity message_chat) {
        this.message_chat = message_chat;
    }

 
}
