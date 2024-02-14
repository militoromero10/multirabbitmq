package edu.javeriana.pipes.filter.utils.consumers;

import edu.javeriana.pipes.filter.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("tweet-filtered")
@Component
@RabbitListener(queues = RabbitMQConfig.FILTER_QUEUE)
public class TweetFilteredConsumer implements Consumer {

    @Override
    @RabbitHandler
    public void consume(String msg) {
        log.info(" [x] Tweet Filtered Received '{}'", msg);
    }
}
