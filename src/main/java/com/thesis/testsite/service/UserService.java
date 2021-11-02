package com.thesis.testsite.service;

import com.thesis.testsite.entity.Message;
import com.thesis.testsite.entity.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    void registerUser(User user);

    List<Message> getMessages();

    List<User> getUsers();

    void addNewMessage(String name, String message);

    User getUserByMessageId(Long id);

    void deleteMessage(Long messageId);

    void updatePassword(String userName, String password);


}
