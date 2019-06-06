package com.journaldev.bootifulmongodb.service;

import com.journaldev.bootifulmongodb.dal.UserMongoReactiveRepository;
import com.journaldev.bootifulmongodb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMongoReactiveRepository userRepository;

    public void create(User e) {
        userRepository.save(e).subscribe();
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Flux<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> update(User e) {
        return userRepository.save(e);
    }

    public Mono<Void> delete(String id) {
        return userRepository.deleteById(id);
    }
}
