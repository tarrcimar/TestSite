package com.thesis.testsite.service;

import com.thesis.testsite.entity.User;
import com.thesis.testsite.repository.MessageRepository;
import com.thesis.testsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        System.out.println(user.getUsername() + " " + user.getPassword());

        return new UserDetailsImpl(user);
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public void registerUser(User user) {

    }
}
