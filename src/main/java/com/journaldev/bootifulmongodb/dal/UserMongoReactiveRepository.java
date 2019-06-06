package com.journaldev.bootifulmongodb.dal;

import com.journaldev.bootifulmongodb.model.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface UserMongoReactiveRepository extends ReactiveMongoRepository<User, String> {
    @Query("{ 'name': ?0 }")
    Flux<User> findByName(final String name);
}
