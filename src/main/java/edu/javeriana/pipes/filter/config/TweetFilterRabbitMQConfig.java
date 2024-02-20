package edu.javeriana.pipes.filter.config;

import edu.javeriana.pipes.filter.utils.consumers.TweetConsumer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.Map;

@Profile("tweet-filter")
@Configuration
public class TweetFilterRabbitMQConfig {

    @Bean
    public Queue queue() {
        return new Queue(RabbitMQConfig.TWEETS_QUEUE, false);
    }

    @Bean
    public Binding filter(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitMQConfig.ROUTE_KEY);
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(TweetConsumer tweetConsumer) {
        return new MessageListenerAdapter(tweetConsumer, "consume");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        var connection = new CachingConnectionFactory();
        connection.setAddresses("localhost:5672");
        connection.setUsername("guest");
        connection.setPassword("guest");
        return connection;
    }

    @Bean
    public ConnectionFactory connectionFactory2() {
        var connection = new CachingConnectionFactory();

        connection.setAddresses("localhost:5673");
        connection.setUsername("guest");
        connection.setPassword("guest");
        return connection;
    }

    @Bean
    @Primary
    public ConnectionFactory connectionFactoryRouter() {
        var con = new SimpleRoutingConnectionFactory();
        con.setTargetConnectionFactories(Map.of(
                "pipes.tweets", connectionFactory(),
                "pipes.filter", connectionFactory2()));
        con.setDefaultTargetConnectionFactory(connectionFactory());
        return con;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory2());
        template.setDefaultReceiveQueue(RabbitMQConfig.TWEETS_QUEUE);
        template.setRoutingKey("pipes.filter");
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactoryRouter());
        container.setQueueNames(RabbitMQConfig.TWEETS_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

}
