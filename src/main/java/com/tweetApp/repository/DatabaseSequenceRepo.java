package com.tweetApp.repository;

import com.tweetApp.modal.DatabaseSequence;
import com.tweetApp.modal.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseSequenceRepo extends MongoRepository<DatabaseSequence, Integer> {
    DatabaseSequence findTopByOrderByIdDesc();
}
