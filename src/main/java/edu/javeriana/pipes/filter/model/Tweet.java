package edu.javeriana.pipes.filter.model;


import jakarta.persistence.*;

@Entity
@Table(name = "tweets")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tweet")
    private String tweet;
    @Column(name = "tag")
    private String tag;

    @Column(name = "average")
    private Integer average;

    public Tweet() {
    }

    public Tweet(Long id, String tweet, String tag, Integer average) {
        this.id = id;
        this.tweet = tweet;
        this.tag = tag;
        this.average = average;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getAverage() {
        return average;
    }

    public void setAverage(Integer average) {
        this.average = average;
    }
}