package edu.javeriana.pipes.filter.utils.consumers;

import edu.javeriana.pipes.filter.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("tweet-filter")
@Component
@RequiredArgsConstructor
@RabbitListener(queues = {RabbitMQConfig.TWEETS_QUEUE})
public class TweetConsumer implements Consumer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    @RabbitHandler
    public void consume(String msg) {
        log.info(" [x] Received '{}' ", msg);
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, "pipes.filter", msg);
    }
}
