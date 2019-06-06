package com.journaldev.bootifulmongodb.service;

import com.journaldev.bootifulmongodb.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    void create(User e);

    Mono<User> findById(String id);

    Flux<User> findByName(String name);

    Flux<User> findAll();

    Mono<User> update(User e);

    Mono<Void> delete(String id);
}
