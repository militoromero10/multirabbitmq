package edu.javeriana.pipes.filter.config;

import edu.javeriana.pipes.filter.utils.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@Profile("tweets")
@EnableScheduling
@Configuration
public class TweetRabbitMQConfig {

    @Bean
    public Queue<Tweet> tweets() {
        var queue = new LinkedList<Tweet>();
        try (var in = new BufferedReader(new FileReader("file.in"))) {
            for (String message; (message = in.readLine()) != null; ) {
                var tweet = Tweet.builder().message(message).build();
                queue.push(tweet);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return queue;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setRoutingKey("pipes.tweets");
        return template;
    }

}
