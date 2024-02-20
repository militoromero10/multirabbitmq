package edu.javeriana.pipes.filter.repositories;

import edu.javeriana.pipes.filter.model.Tweet;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile({"tweet-filtered"})
@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    Tweet findByTag(String tag);
}
