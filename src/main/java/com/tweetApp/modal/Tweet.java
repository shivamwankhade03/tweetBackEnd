package com.tweetApp.modal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Document
public class Tweet implements Comparable<Tweet>{

    @Id
    private String id;
    private String tweetMsg;
    private String userId;
    private String firstName;
    private String LastName;
    private Date time;
    private String like;
    private List<String> likeBy = new ArrayList<>();

    private List<Comment> commentList = new ArrayList<>();

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTweetMsg() {
        return tweetMsg;
    }

    public void setTweetMsg(String tweetMsg) {
        this.tweetMsg = tweetMsg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public Date  getTime() {
        return time;
    }

    public void setTime(Date  time) {
        this.time = time;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public List<String> getLikeBy() {
        return likeBy;
    }

    public void setLikeBy(List<String> likeBy) {
        this.likeBy = likeBy;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id='" + id + '\'' +
                ", tweetMsg='" + tweetMsg + '\'' +
                ", userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", time=" + time +
                ", like='" + like + '\'' +
                ", likeBy=" + likeBy +
                ", commentList=" + commentList +
                '}';
    }


    @Override
    public int compareTo(Tweet o) {
        if(time.after(o.time))
            return -1;
        else if(time == o.time)
            return 0;
        else
            return 1;
    }
}
