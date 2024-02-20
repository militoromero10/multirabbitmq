package edu.javeriana.pipes.filter.services.impl;

import edu.javeriana.pipes.filter.model.Tweet;
import edu.javeriana.pipes.filter.repositories.TweetRepository;
import edu.javeriana.pipes.filter.services.TweetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Profile({"tweet-filtered"})
@Service
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;

    public TweetServiceImpl(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @Override
    public void save(Tweet tweet) {
        System.out.println(String.format("Save here tweet [%s] tag: [%s]", tweet.getTweet(),tweet.getTag()));

//        var tweetDb = tweetRepository.findByTag(tweet.getTag());
//        if(Objects.nonNull(tweetDb)){
//            tweetDb.setAverage(tweet.getAverage()+1);
//        }
//        tweetRepository.save(tweetDb);
        tweetRepository.save(tweet);
    }
}
