package org.interview.infrastructure.config;

import com.google.api.client.http.HttpRequestFactory;
import lombok.extern.slf4j.Slf4j;
import org.interview.infrastructure.oauth.twitter.TwitterAuthenticationException;
import org.interview.infrastructure.oauth.twitter.TwitterAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for Twitter API authentication
 *
 * @author Fabio Bozzo
 */
@Configuration
@Slf4j
public class TwitterConfig {

  @Value("${twitter.consumer-key}")
  private String consumerKey;

  @Value("${twitter.consumer-secret}")
  private String consumerSecret;

  @Bean
  public TwitterAuthenticator twitterAuthenticator() {
    return new TwitterAuthenticator(System.out, consumerKey, consumerSecret);
  }

  @Bean
  public HttpRequestFactory authorizedHttpRequestFactory() throws TwitterAuthenticationException {
    log.debug("Authenticating...");
    HttpRequestFactory authorizedHttpRequestFactory = twitterAuthenticator().getAuthorizedHttpRequestFactory();
    log.debug("Authenticated! ");
    return authorizedHttpRequestFactory;
  }
}
