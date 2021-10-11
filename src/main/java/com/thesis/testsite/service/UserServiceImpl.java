package com.thesis.testsite.service;

import com.thesis.testsite.entity.Message;
import com.thesis.testsite.entity.User;
import com.thesis.testsite.repository.MessageRepository;
import com.thesis.testsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;
    private MessageRepository messageRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MessageRepository messageRepository){
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("user keresése: " + username);
        User user = findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        System.out.println(user.getUsername() + " " + user.getPassword());

        return new UserDetailsImpl(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void registerUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addNewMessage(String name, String message) {
        User user = findByUsername(name);
        user.addMessages(message);
        userRepository.save(user);
    }

    @Override
    public User getUserByMessageId(Long id) {
        return userRepository.getUserByMessageId(id);
    }

    @Override
    public void deleteMessage(Long messageId) {
        messageRepository.deleteMessageById(messageId);
    }

    @Override
    public void updatePassword(String userName, String password) {
        userRepository.changePassword(userName, password);
    }
}
