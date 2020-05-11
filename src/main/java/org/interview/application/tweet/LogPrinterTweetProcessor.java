package org.interview.application.tweet;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.interview.domain.tweet.Tweet;
import org.interview.domain.tweet.TweetListTransformer;
import org.interview.domain.tweet.TweetProcessor;
import org.interview.utils.DateTimeUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * TweetProcessor printing a list of tweets to STDOUT
 *
 * @author Fabio Bozzo
 */
@Profile("default")
@Component
@Slf4j
@RequiredArgsConstructor
public class LogPrinterTweetProcessor implements TweetProcessor {

  private final TweetListTransformer tweetListTransformer;

  /**
   * {@inheritDoc}
   *
   * This implementation uses slf4j logging to write output to stdout (console appender)
   */
  @Override
  public void process(List<Tweet> tweets) {

    val authorsMap = tweetListTransformer.groupTweetsByAuthorSorted(tweets);

    authorsMap.forEach(((author, tweetsPerAuthor) -> {
      log.info("Tweets by {} ({}): ", author.getName(), DateTimeUtils.formatDate(author.getCreationDate()));
      tweetsPerAuthor.forEach(
          tweet -> log.info("[{}] {}", DateTimeUtils.formatDateTime(tweet.getCreationDate()), tweet.getText())
      );
    }));
  }
}
