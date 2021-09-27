package com.ale.userqueryservice.config;


import com.ale.userqueryservice.model.UserDto;
import com.ale.userqueryservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.streams.kstream.KStream;
import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.webflux.dsl.WebFlux;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class UserKafkaConsumer {
    private final UserService userService;

    @Bean
    public Publisher<Message<UserDto>> userAddedEventPublisher() {
        return IntegrationFlows.
                from(WebFlux.inboundChannelAdapter("/event/{id}")
                        .requestMapping(r -> r.methods(HttpMethod.POST)
                                .consumes("application/json")))
                .transform(new JsonToObjectTransformer(UserDto.class))
                .channel(MessageChannels.publishSubscribe())
                .toReactivePublisher();
    }

//    @Bean("userEventService")
//    public Consumer<KStream<String, UserDto>> userEventService() {
//        return kStream -> kStream.foreach((key, user) -> {
//            userService.addUserToCb(user).subscribe();
//            log.info(
//                    String.format("User added event received:  %s, %s,%s ",
//                            user.getFirstName(),
//                            user.getLastName(),
//                            user.getEmail())
//            );
//        });
//    }
}
