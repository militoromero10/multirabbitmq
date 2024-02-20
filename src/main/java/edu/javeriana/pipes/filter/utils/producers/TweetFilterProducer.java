package edu.javeriana.pipes.filter.utils.producers;

import edu.javeriana.pipes.filter.config.RabbitMQConfig;
import edu.javeriana.pipes.filter.utils.Tweet;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Queue;

@Profile("tweets")
@Component
public class TweetFilterProducer implements Producer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue<Tweet> tweetsQueue;

    public TweetFilterProducer(RabbitTemplate rabbitTemplate, Queue<Tweet> tweetsQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.tweetsQueue = tweetsQueue;
    }

    @Scheduled(fixedRate = 100)
    public void send() {
        if (!CollectionUtils.isEmpty(tweetsQueue)) {
            Tweet tweet = tweetsQueue.poll();
            System.out.println(String.format("Sending [%s]", tweet.message()));
            rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, "pipes.tweets", tweet.message());
        } else {
            System.out.println("Tweets empty");
        }
    }
}
