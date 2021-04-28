package com.tweetApp.exception;

public class PostTweetFailedException extends RuntimeException{

    public PostTweetFailedException(String s) {
        super(s);
    }
}
