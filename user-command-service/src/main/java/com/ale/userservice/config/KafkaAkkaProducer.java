package com.ale.userservice.config;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.kafka.ProducerSettings;
import akka.kafka.javadsl.Producer;
import akka.stream.javadsl.Source;
import com.typesafe.config.Config;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaAkkaProducer {

    private final String TOPIC = "users-topic";

    private static final ActorSystem<Void> system = ActorSystem.create(Behaviors.empty(), "Producer");

    final Config config = system
            .settings()
            .config();
//            .getConfig("akka.kafka.producer");

    final ProducerSettings<String, String> producerSettings =
            ProducerSettings.create(config, new StringSerializer(), new StringSerializer())
                    .withBootstrapServers("localhost:9092");

    public void sendMessage(String message){
        Source.single(message)
                .map(value -> new ProducerRecord<String, String>(TOPIC, value))
                .runWith(Producer.plainSink(producerSettings), system );
    }

}
