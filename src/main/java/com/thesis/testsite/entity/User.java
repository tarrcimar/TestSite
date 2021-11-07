package com.thesis.testsite.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
public class User {

    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    @Column(nullable = false)
    @Length(min = 5, max = 10)
    private String username;

    @Lob
    @Column(nullable = false)
    @Length(min = 8, max = 20)
    private String password;

    private String role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Message> messages;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.messages = messages;
        if(role.equals("admin")){
            this.role = ROLE_ADMIN;
        }
        else
            this.role = ROLE_USER;
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

    public void setRole(String role) {
        if(role.equals("admin")){
            this.role = ROLE_ADMIN;
        }
        else
            this.role = ROLE_USER;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}