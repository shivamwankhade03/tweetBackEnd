package com.tweetApp.service.impl;

import com.mongodb.MongoWriteException;
import com.tweetApp.exception.EmailIdAlreadyExitsException;
import com.tweetApp.exception.InvalidInputException;
import com.tweetApp.exception.UserNameAlreadyExistsException;
import com.tweetApp.exception.UserNotFoundException;
import com.tweetApp.modal.LoginDetails;
import com.tweetApp.modal.User;
import com.tweetApp.repository.UserRepo;
import com.tweetApp.service.UserService;
import com.tweetApp.utility.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceimpl implements UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserServiceimpl.class);
    @Autowired
    UserRepo userRepo;

    @Override
    public String registerNewUser(User resgistrationDetails) {
        Optional<User> isuserExits = userRepo.findByEmail(resgistrationDetails.getEmail());

        if(isuserExits.isPresent()) throw new EmailIdAlreadyExitsException("Email Id already exits");
        try{
            userRepo.insert(resgistrationDetails);
        }catch (DuplicateKeyException e){
                throw new UserNameAlreadyExistsException(e.getMessage());
        }
        catch (MongoWriteException e){
            System.out.println("Exception in user insertion "+e.getMessage());
        }
        return Constants.SUCCESS;
    }

    @Override
    public User authenticateLogin(LoginDetails loginDetails) {
       LOGGER.info("Login Details {}",loginDetails.toString());

        if(loginDetails.getUserName().isEmpty() || loginDetails.getUserName().isEmpty() ||
                loginDetails.getUserName()==null || loginDetails.getUserName()==null){

            throw  new InvalidInputException(" Enter valid details inputs are empty ");
        }
        Optional<User> i =userRepo.findById(loginDetails.getUserName());
        if(i.isPresent()){
            if(loginDetails.getPassword().equals(i.get().getPassword())){
                return i.get();
            }

        }
        throw  new InvalidInputException(" Enter valid details inputs are empty ");
    }

    @Override
    public boolean forgotPassword(LoginDetails loginDetails) {

        Optional<User> userInfo =userRepo.findByEmail(loginDetails.getUserName());
        if(userInfo.isPresent()){
            User u =  userInfo.get();
            u.setPassword(loginDetails.getPassword());
            userRepo.save(u);
            return true;
        }else{
            throw new EmailIdAlreadyExitsException("User with this email not exits");
        }
    }

    @Override
    public User getUserById(String id) {
        Optional<User> userInfo = userRepo.findById(id);

        if(!userInfo.isPresent()){
            throw new UserNotFoundException("User not found exception");
        }
        LOGGER.info("User Details {} ",userInfo.toString());

        return userInfo.get();

    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        userList = userRepo.findAll();
        if(userList==null){
            throw  new UserNotFoundException("List is empty");
        }
        return userList;
    }
}
