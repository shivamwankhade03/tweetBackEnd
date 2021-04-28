package com.tweetApp.service.impl;

import com.tweetApp.exception.PostTweetFailedException;
import com.tweetApp.exception.ResourceNotFoundException;
import com.tweetApp.modal.Comment;
import com.tweetApp.modal.Tweet;
import com.tweetApp.modal.User;
import com.tweetApp.repository.TweetRepo;
import com.tweetApp.repository.UserRepo;
import com.tweetApp.service.TweetService;
import com.tweetApp.utility.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TweetServiceImpl implements TweetService {

    public static final Logger LOGGER = LoggerFactory.getLogger(TweetServiceImpl.class);
    @Autowired
    UserRepo userRepository;

    @Autowired
    TweetRepo tweetRepository;

    @Override
    public String postTweet(String userId, Tweet tweet) {
        Optional<User> userInfo= userRepository.findById(userId);
        LOGGER.info("User Details : {} ",userInfo);

        Tweet tweetObj = new Tweet();
        tweetObj.setTweetMsg(tweet.getTweetMsg());
        tweetObj.setFirstName(tweet.getFirstName());
        tweetObj.setLastName(tweet.getLastName());
        //LocalDateTime time = LocalDateTime.now();
        tweetObj.setTime(new Date());
        tweetObj.setLike(String.valueOf(0));
        tweetObj.setCommentList(new ArrayList<>());
        tweetObj.setUserId(userId);
        LOGGER.info("Tweet {}",tweetObj.toString());
        Tweet tObj = tweetRepository.insert(tweetObj);

        if(!tObj.getId().isEmpty()){
            return Constants.SUCCESS;
        }

/*

        Update update = new Update();
        update.addToSet("tweetsList",t.getId());
        Criteria criteria = Criteria.where("_id").is(userId);
        mongoTemplate.updateFirst(Query.query(criteria), update, "tweets");

*/
        throw new PostTweetFailedException("Tweet posting failed");
    }

    @Override
    public List<Tweet> getAllTweets() {
        List<Tweet> tweetList=null;
        tweetList = tweetRepository.findAll();
        Collections.sort(tweetList);

        for (Tweet t: tweetList
             ) {
            LOGGER.info("Tweet : {}",t.toString());
        }
        return tweetList;
    }

    @Override
    public List<Tweet> getTweetByUserId(String userId) {
        List<Tweet> tweetlist = null;
        tweetlist = tweetRepository.findByUserId(userId);
        Collections.sort(tweetlist);
        return tweetlist;
    }

    @Override
    public  Tweet updateTweet(String userId, String id, String updatedMsg) {
        LOGGER.info("Inside update tweet method");
        Optional<Tweet> tweet = tweetRepository.findById(id);
 //       Optional<Tweet> tweet = Optional.ofNullable(tweetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException()));;
        Tweet newTweet = new Tweet();
        if(tweet.isPresent() && tweet.get().getId()!=null){
           // Query query1 = new Query(Criteria.where("_id").is(id));
           // mongoTemplate.findAndModify(query1, Update.update("tweetMsg",updatedMsg), Tweet.class);
           newTweet = tweet.get();
           newTweet.setTweetMsg(updatedMsg);
           newTweet.setTime(new Date());

           newTweet = tweetRepository.save(newTweet);
            System.out.println("Updated successfullly");
        }
        return newTweet;
    }

    @Override
    public boolean deleteTweet(String id) {

        Optional<Tweet> tweet = tweetRepository.findById(id);
        if(tweet.isPresent() && tweet.get().getId()!=null){
            tweetRepository.deleteById(id);
            return true;
        }
        return false;}
    @Override
    public List<String> likeTweet(String userId, String id, String type) {
        LOGGER.info("Service : "+id);
        //Optional<Tweet> tweet = Optional.ofNullable(tweetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException()));;
        Optional<Tweet> tweet = tweetRepository.findById(id);

        System.out.println("Id is ====>: "+tweet.get().toString());
        Tweet newTweet = new Tweet();
        List<String> likeByUser = new ArrayList<>();

        if(tweet.isPresent() && tweet.get().getId()!=null){
            newTweet = tweet.get();
            likeByUser =  newTweet.getLikeBy();
            if(type.contains("true")){
              likeByUser.add(userId);
                if(Integer.parseInt(newTweet.getLike())>=0){
                    newTweet.setLike( String.valueOf(Integer.parseInt(newTweet.getLike()) + 1));
                }
            }else{
                likeByUser.remove(userId);
                if(Integer.parseInt(newTweet.getLike())>0){
                    newTweet.setLike(String.valueOf(Integer.parseInt(newTweet.getLike()) - 1));
                }
            }
            newTweet.setLikeBy(likeByUser);
            tweetRepository.save(newTweet);

            return newTweet.getLikeBy();
        }
        return newTweet.getLikeBy();
    }

    @Override
    public Tweet replyTweet(String userId, String id, String reply) {
        LOGGER.info("Service : {} and Reply {} ",id,reply);
        Optional<Tweet> tweet = tweetRepository.findById(id);

        Tweet newTweet = tweet.get();
        if(tweet.isPresent() && tweet.get().getId()!=null){
            // Query query1 = new Query(Criteria.where("_id").is(id));
            // mongoTemplate.findAndModify(query1, Update.update("tweetMsg",updatedMsg), Tweet.class);

            List<Comment> commentList = new ArrayList<>();
            commentList = newTweet.getCommentList();

            Comment replyToTweet = new Comment();
            replyToTweet.setText(reply);
            replyToTweet.setCommentBy(userId);


            if(tweet.get().getCommentList() == null || tweet.get().getCommentList().size() ==0){
                commentList.add(replyToTweet);
                newTweet.setCommentList(commentList);
            }else{

                commentList.add(replyToTweet);
                newTweet.setCommentList(commentList);

            }
            newTweet = tweetRepository.save(newTweet);
            LOGGER.info("Updated successfullly {}",newTweet.toString());
        }
        return newTweet;
    }
}
