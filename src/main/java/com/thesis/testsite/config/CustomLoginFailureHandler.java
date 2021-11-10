package com.thesis.testsite.config;

import com.thesis.testsite.entity.User;
import com.thesis.testsite.service.LogService;
import com.thesis.testsite.service.UserService;
import com.thesis.testsite.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.locks.Lock;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private UserServiceImpl userServiceImpl;

    @Autowired
    public void setUserServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    private LogService logService;

    @Autowired
    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("FAILURE");
        String username = request.getParameter("username");
        System.out.println(request.getParameter("username"));
        User user = userService.findByUsername(username);

        if(user!=null){
            if(user.isAccount_non_locked()){
                System.out.println("Attempt in Failure: " + user.getFailedAttempt());
                if(user.getFailedAttempt() < userServiceImpl.MAX_FAILED_ATTEMPTS -1){
                    userService.increaseAttempts(user);
                    exception = new BadCredentialsException("Wrong username or password");
                }
                else {
                    userService.lock(user);
                    exception = new LockedException("Your account has been locked due to 3 failed attempts!");
                }
            }
        }

        logService.createLog("User: " + username + " login failed.", "WARN");
        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
