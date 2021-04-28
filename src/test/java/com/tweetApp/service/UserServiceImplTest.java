package com.tweetApp.service;


import com.tweetApp.exception.EmailIdAlreadyExitsException;
import com.tweetApp.exception.UserNameAlreadyExistsException;
import com.tweetApp.exception.UserNotFoundException;
import com.tweetApp.modal.User;
import com.tweetApp.repository.UserRepo;
import com.tweetApp.service.impl.UserServiceimpl;
import com.tweetApp.utility.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    UserRepo userRepoImpl;

    @InjectMocks
    UserServiceimpl userServiceimpl;

    @Test
    public void getAllUsers(){

        List<User> uList = new ArrayList<>();
        User u = new User();
        u.setlogInId("swankhade");
        u.setEmail("s@gmail.com");
        uList.add(u);
        when(userRepoImpl.findAll()).thenReturn(uList);
        List<User> userList= userServiceimpl.getAllUsers();
        assertEquals(1,userList.size());

    }

    @Test(expected = UserNotFoundException.class)
    public void getAllUsersEmpty(){

        List<User> uList = null;
        when(userRepoImpl.findAll()).thenReturn(uList);
        List<User> userList= userServiceimpl.getAllUsers();
    }

    @Test(expected = NullPointerException.class)
    public void getUserByIdException(){
        Optional<User> user = null;
        when(userRepoImpl.findById("swankhade")).thenReturn(user);
        User u = userServiceimpl.getUserById("swankhade");

    }

    @Test
    public void getUserById(){
        User user = new User();
        user.setEmail("s@gmail.com");
        user.setlogInId("swankhade");

        when(userRepoImpl.findById("swankhade")).thenReturn(Optional.of(user));
        User u = userServiceimpl.getUserById("swankhade");

        assertEquals("swankhade",u.getlogInId());
    }


    @Test
    public void registerNewUser(){
        User resgistrationDetails = new User();
        String flag = userServiceimpl.registerNewUser(resgistrationDetails);
        assertEquals(Constants.SUCCESS,flag);
    }


    @Test(expected = EmailIdAlreadyExitsException.class)
    public void registerNewUserException(){
        User resgistrationDetails = new User();
        resgistrationDetails.setlogInId("swankhade");
        resgistrationDetails.setEmail("s@gmail.com");
        Optional<User> isuserExits = Optional.of(new User());
        when(userRepoImpl.findByEmail("s@gmail.com")).thenReturn(isuserExits);
        String flag = userServiceimpl.registerNewUser(resgistrationDetails);
        assertEquals(Constants.SUCCESS,flag);
    }


}
