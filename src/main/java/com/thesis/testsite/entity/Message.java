package com.thesis.testsite.entity;

import javax.persistence.*;

@Table(name = "messages")
@Entity
public class Message {

    @Id
    @GeneratedValue
    private Long id;


    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Message(String content) {
        this.content = content;
    }

    public Message() {

    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}