package com.tweetApp.controller;


import com.tweetApp.exception.PostTweetFailedException;
import com.tweetApp.modal.LoginDetails;
import com.tweetApp.modal.Tweet;
import com.tweetApp.modal.User;
import com.tweetApp.service.TweetService;
import com.tweetApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping ("/tweet")
public class UserController {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userServiceimpl;

    @Autowired
    TweetService tweetService;
/*
    @GetMapping("/all")
    public List<User> getAll() {
        System.out.println("Get All");
        return userRepository.findAll();
    }
*/
    @GetMapping("/users/all")
    public ResponseEntity<List<User>> getAllUsers() {
    return new ResponseEntity(userServiceimpl.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String id) {
        return new ResponseEntity(userServiceimpl.getUserById(id),HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody User resgistrationDetails) {
        System.out.println("Add user"+resgistrationDetails.toString());
       return new ResponseEntity(userServiceimpl.registerNewUser(resgistrationDetails),HttpStatus.OK);
    }

    //login method use post rather than Get
    @PostMapping("/login")
    public ResponseEntity<User> authenticateLogin(@RequestBody LoginDetails loginDetails) {
        LOGGER.info("Add user");
        return new ResponseEntity(userServiceimpl.authenticateLogin(loginDetails),HttpStatus.OK);
    }

    @PostMapping("/forgot")
    public ResponseEntity<Boolean> forgotPassword(@RequestBody LoginDetails loginDetails) {
        return new ResponseEntity(userServiceimpl.forgotPassword(loginDetails),HttpStatus.OK);
    }


    @PostMapping("/{userId}/add")
    public ResponseEntity<String> postTweet(@PathVariable("userId") String id,@RequestBody Tweet tweet){
       try {
           return new ResponseEntity(tweetService.postTweet(id, tweet), HttpStatus.OK);
       }catch (PostTweetFailedException e){
           return new ResponseEntity("Tweet posting failed", HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @GetMapping("/all")
    public List<Tweet> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @GetMapping("/{userName}")
    public ResponseEntity<List<Tweet>> getTweetsByUserId(@PathVariable("userName") String userId) {
        return new ResponseEntity(tweetService.getTweetByUserId(userId),HttpStatus.OK);
    }

    @PutMapping("/update/{userId}/{id}")
    public ResponseEntity<Tweet> updateTweet(@PathVariable("userId") String userId,@PathVariable("id") String id,@RequestBody String updatedMsg) {
        return  new ResponseEntity(tweetService.updateTweet(userId,id,updatedMsg),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteTweet(@PathVariable("id") String id) {

        return new ResponseEntity(tweetService.deleteTweet(id),HttpStatus.OK);
    }

    @PostMapping("/like/{userId}/{id}/{type}")
    public ResponseEntity<List<String>> likeTweet(@PathVariable("userId") String userId ,@PathVariable("id") String tweetId,@PathVariable("type") String type){
        return new ResponseEntity(tweetService.likeTweet(userId,tweetId,type),HttpStatus.OK);
    }

    @PostMapping("/reply/{userId}/{id}")
    public ResponseEntity replyTweet(@PathVariable("userId") String userId,@PathVariable("id") String id,@RequestBody String reply){
        return new ResponseEntity(tweetService.replyTweet(userId, id, reply),HttpStatus.OK);
    }
}
