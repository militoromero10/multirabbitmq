package edu.javeriana.pipes.filter.utils.consumers;

import edu.javeriana.pipes.filter.config.RabbitMQConfig;
import edu.javeriana.pipes.filter.model.Tweet;
import edu.javeriana.pipes.filter.services.TweetService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Profile("tweet-filtered")
@Component
@RabbitListener(queues = RabbitMQConfig.FILTER_QUEUE)
public class TweetFilteredConsumer implements Consumer {

    private final TweetService tweetService;

    public TweetFilteredConsumer(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @Override
    @RabbitHandler
    public void consume(String msg) {
        System.out.println(String.format(" [x] Tweet Filtered Received '%s'", msg));
        var tag = msg.split("#");

        var tweet = new Tweet();
        tweet.setTweet(String.format("%s - %s", UUID.randomUUID(), tag[0]));
        tweet.setTag(tag[1]);
        tweetService.save(tweet);
    }
}
