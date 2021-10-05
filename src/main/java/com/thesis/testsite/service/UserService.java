package com.thesis.testsite.service;

import com.thesis.testsite.entity.User;

public interface UserService {

    User findByUsername(String username);

    void registerUser(User user);

}
