package org.interview;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.interview.domain.tweet.Tweet;
import org.interview.domain.tweet.TweetProcessor;
import org.interview.domain.tweet.TwitterStreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application entry point
 *
 * @author Fabio Bozzo
 */
@SpringBootApplication
@Slf4j
public class App implements CommandLineRunner {

  @Value("${twitter.stream.filter-keyword}")
  private String defaultFilterKeyword;

  @Autowired
  private TwitterStreamClient twitterStreamClient;

  @Autowired
  private TweetProcessor tweetProcessor;

  public static void main(String[] args) {
    SpringApplication.run(App.class, assertArgsNotNull(args));
  }

  @Override
  public void run(String... args) {
    log.info("App STARTED...");

    val keyword =  args.length > 0 ? args[0] : defaultFilterKeyword;

    log.info("Retrieving tweets by keyword: {}", keyword);
    Instant start = Instant.now();
    List<Tweet> tweets = twitterStreamClient.readByFilterKeyword(keyword);
    Instant finish = Instant.now();
    log.info("{} tweets retrieved in {} ms", tweets.size(), Duration.between(start, finish).toMillis());

    log.info("Processing tweets...");
    start = Instant.now();
    tweetProcessor.process(tweets);
    finish = Instant.now();
    log.info("Finished processing tweets in {} ms", Duration.between(start, finish).toMillis());
  }

  private static String[] assertArgsNotNull(String[] args) {
    return Objects.isNull(args) ? new String[] {} : args;
  }
}