package edu.javeriana.pipes.filter.utils.consumers;

import edu.javeriana.pipes.filter.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("tweet-filter")
@Component
@RabbitListener(queues = {RabbitMQConfig.TWEETS_QUEUE})
public class TweetConsumer implements Consumer {

    private final String[] values;
    private final RabbitTemplate rabbitTemplate;

    public TweetConsumer(RabbitTemplate rabbitTemplate, @Value("${TAGS:machinelearning,ml}") String[] values) {
        this.rabbitTemplate = rabbitTemplate;
        this.values = values;
    }

    @Override
    @RabbitHandler
    public void consume(String msg) {
        System.out.println(String.format(" [x] Received '%s'", msg));
        for(String tag : values){
            if(msg.contains(tag)){
                rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, "pipes.filter", msg);
            }
        }
    }
}
