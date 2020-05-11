package org.interview.application.tweet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.interview.application.exception.TwitterStreamClientException;
import org.interview.domain.tweet.Tweet;
import org.interview.domain.tweet.TwitterStreamClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * TwitterStreamClient threshold-based implementation
 *
 * @author Fabio Bozzo
 */
@Component
@RequiredArgsConstructor
public class ThresholdTwitterStreamClient implements TwitterStreamClient {

  private final HttpRequestFactory httpRequestFactory;
  private final ObjectMapper objectMapper;

  @Value("${twitter.stream.endpoint}")
  private String endpointUrl;

  @Value("${twitter.stream.max-tweets}")
  private Long tweetsCountThreshold;

  @Value("${twitter.stream.max-time-elapsed-ms}")
  private Long tweetsTimeThreshold;

  /**
   * {@inheritDoc}
   *
   * This implementation reads realtime statuses up to a fixed amount of tweets and/or milliseconds
   */
  @Override
  public List<Tweet> readByFilterKeyword(String filterKeyword) {

    try {

      HttpRequest request = httpRequestFactory.buildGetRequest(new GenericUrl(
          String.format("%s?track=%s", endpointUrl, filterKeyword)
      ));

      try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.execute().getContent()))) {

        var tweets = new ArrayList<Tweet>();
        var counter = 0L;
        val startInstant = Instant.now();

        while (reader.ready() && !timeThresholdExceeded(startInstant) && !countThresholdExceeded(counter)) {
          tweets.add(objectMapper.readValue(reader.readLine(), Tweet.class));
          counter++;
        }

        return tweets;
      }

    } catch (IOException e) {
      throw new TwitterStreamClientException("Cannot retrieve tweets: ", e);
    }

  }

  private boolean countThresholdExceeded(Long counter) {
    return counter >= tweetsCountThreshold;
  }

  private boolean timeThresholdExceeded(Instant startInstant) {
    return Duration.between(startInstant, Instant.now()).toMillis() > tweetsTimeThreshold;
  }
}
