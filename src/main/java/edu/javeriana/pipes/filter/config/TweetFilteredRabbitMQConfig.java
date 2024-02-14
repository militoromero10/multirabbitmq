package edu.javeriana.pipes.filter.config;

import edu.javeriana.pipes.filter.utils.consumers.TweetFilteredConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Profile("tweet-filtered")
@Configuration
public class TweetFilteredRabbitMQConfig {

    @Bean
    public Queue queue() {
        return new Queue(RabbitMQConfig.FILTER_QUEUE, false);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitMQConfig.ROUTE_KEY);
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(TweetFilteredConsumer tweetFilteredConsumer) {
        return new MessageListenerAdapter(tweetFilteredConsumer, "consume");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        var connection = new CachingConnectionFactory();
        connection.setAddresses("localhost:5673");
        connection.setUsername("guest");
        connection.setPassword("guest");
        return connection;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RabbitMQConfig.FILTER_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

}
