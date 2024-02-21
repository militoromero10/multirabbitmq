package edu.javeriana.pipes.filter.services.impl;

import edu.javeriana.pipes.filter.model.Tweet;
import edu.javeriana.pipes.filter.repositories.TweetRepository;
import edu.javeriana.pipes.filter.services.TweetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Random;

@Profile({"tweet-filtered"})
@Service
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final Random random;

    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
        this.random = new Random();
    }

    @Override
    public void save(Tweet tweet) {
        System.out.println(String.format("Save here tweet [%s] tag: [%s]", tweet.getTweet(),tweet.getTag()));
        var lower = (char)random.nextInt(97, 123); //a-z
        var upper = (char)random.nextInt(65, 91); //A-Z
        var number = (char)random.nextInt(48, 58); //0-9
        var msg = tweet.getTweet().replaceAll(lower+"", "");
        msg = msg.replaceAll(upper+"","");
        msg = msg.replaceAll(number+"","");
        tweet.setTweet(msg);
        tweet.setAverage(msg.length());
//        var tweetDb = tweetRepository.findByTag(tweet.getTag());
//        if(Objects.nonNull(tweetDb)){
//            tweetDb.setAverage(tweet.getAverage()+1);
//        }
//        tweetRepository.save(tweetDb);
        tweetRepository.save(tweet);
    }}
