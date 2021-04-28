package com.tweetApp.service;


import com.tweetApp.modal.Tweet;

import java.util.List;

public interface TweetService {

    String postTweet(String userId, Tweet tweet);
    List<Tweet> getAllTweets();
    List<Tweet> getTweetByUserId(String userId) ;
    Tweet updateTweet(String userId,String id,String updatedMsg);
    boolean deleteTweet(String id);
    List<String> likeTweet(String userId,String userid,String type);
    Tweet replyTweet(String userId,String id, String reply);
}
