package com.journaldev.bootifulmongodb.dal;

import com.journaldev.bootifulmongodb.model.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {
}
