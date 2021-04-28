package com.tweetApp.service;

import com.tweetApp.modal.LoginDetails;
import com.tweetApp.modal.User;

import java.util.List;


public interface UserService {
    String registerNewUser(User resgistrationDetails);
    User authenticateLogin(LoginDetails loginDetails) ;
    boolean forgotPassword(LoginDetails loginDetails);
    User getUserById(String id);
    List<User> getAllUsers();

}

