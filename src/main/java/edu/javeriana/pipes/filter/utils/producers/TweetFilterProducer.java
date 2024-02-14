package edu.javeriana.pipes.filter.utils.producers;

import edu.javeriana.pipes.filter.config.RabbitMQConfig;
import edu.javeriana.pipes.filter.utils.Tweet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Queue;

@Profile("tweets")
@Slf4j
@Component
@RequiredArgsConstructor
public class TweetFilterProducer implements Producer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue<Tweet> tweetsQueue;

    @Scheduled(fixedRate = 100)
    public void send() {
        if (!CollectionUtils.isEmpty(tweetsQueue)) {
            Tweet tweet = tweetsQueue.poll();
            log.info("Sending {} - [{}]", tweet.message(), rabbitTemplate.getRoutingKey());
            rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, "pipes.tweets", tweet.message());
        } else {
            log.info("Tweets empty");
        }
    }
}
