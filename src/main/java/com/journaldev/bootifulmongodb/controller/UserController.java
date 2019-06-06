package com.journaldev.bootifulmongodb.controller;

import com.journaldev.bootifulmongodb.model.User;
import com.journaldev.bootifulmongodb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = { "/create", "/" }, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void create(@RequestBody User e) {
        userService.create(e);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Mono<User>> findById(@PathVariable("id") String id) {
        Mono<User> e = userService.findById(id);
        HttpStatus status = e != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<Mono<User>>(e, status);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Flux<User> findByName(@PathVariable("name") String name) {
        return userService.findByName(name);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<User> findAll() {
        Flux<User> emps = userService.findAll();
        return emps;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Mono<User> update(@RequestBody User e) {
        return userService.update(e);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id) {
        userService.delete(id).subscribe();
    }

}
