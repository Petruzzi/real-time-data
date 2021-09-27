package com.ale.userqueryservice.config;

import akka.actor.typed.ActorSystem;
import akka.kafka.ConsumerSettings;
import akka.kafka.Subscriptions;
import akka.kafka.javadsl.Consumer;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import com.ale.userqueryservice.behavior.ManagerBehavior;
import com.ale.userqueryservice.model.UserDto;
import com.ale.userqueryservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static akka.actor.typed.javadsl.Adapter.toClassic;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KafkaAkkaConsumer {

    @Autowired
    private final UserService userService;

    private final String TOPIC = "users-topic";

    private static final ActorSystem<ManagerBehavior.Command> system = ActorSystem.create(ManagerBehavior.create(), "ProbablePrimeSystem");

    protected  final ConsumerSettings<byte[], String> consumerSettings =
            ConsumerSettings.create(toClassic(system), new ByteArrayDeserializer(), new StringDeserializer())
                    .withBootstrapServers("localhost:9092")
                    .withGroupId("group");
//                    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    @Bean
    public void createConsumer() {
        Consumer.plainSource(consumerSettings, Subscriptions.topics(TOPIC))
                .map((reuslt) -> new ObjectMapper().readValue(reuslt.value(), UserDto.class))
                .runForeach((user) ->
                        {
                            userService.addUserToCb(user).subscribe();
                            system.tell(new ManagerBehavior.InstructionCommand("start"));
                            log.info(
                                    String.format("User added event received:  %s, %s,%s ",
                                            user.getFirstName(),
                                            user.getLastName(),
                                            user.getEmail()));
                        }
                        , system);
    }
}
