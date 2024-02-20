package edu.javeriana.pipes.filter.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!tweet-generator")
@Configuration
public class RabbitMQConfig {

    public static final String TOPIC_EXCHANGE_NAME = "pipes-filters";

    public static final String TWEETS_QUEUE = "tweets";
    public static final String FILTER_QUEUE = "filter";
    public static final String ROUTE_KEY = "pipes.#";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

}
