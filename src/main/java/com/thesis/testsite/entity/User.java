package com.thesis.testsite.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
public class User {

    private static final String ROLE_USER = "USER";

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String password;

    private String role = ROLE_USER;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Message> messages;

    public User(String username, String password, List<Message> messages) {
        this.username = username;
        this.password = password;
        this.messages = messages;
    }

    public User() {

    }

    public void addMessages(String message){
        if(this.messages == null || this.messages.isEmpty())
            this.messages = new ArrayList<>();
        this.messages.add(new Message(message));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}