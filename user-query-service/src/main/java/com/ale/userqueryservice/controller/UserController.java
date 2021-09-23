package com.ale.userqueryservice.controller;

import com.ale.userqueryservice.model.UserDto;
import com.ale.userqueryservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/user")

@Slf4j
public class UserController {

    private final Flux<UserDto> events;


    public UserController(@Autowired final Publisher<Message<UserDto>> gameEventPublisher,
                          @Autowired final UserService userService) {
        Flux<UserDto> users = userService.getAllUsers();

        this.events = Flux.concat(users, Flux.from(gameEventPublisher)
                .map(Message::getPayload));
    }

    @GetMapping(value = "/all/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserDto> streamUsers() {
        return this.events;
    }
}
