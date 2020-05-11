package org.interview.domain.tweet;

import java.util.List;

public interface TweetProcessor {

  /**
   * Processes a list of Tweets
   *
   * @param tweets The list of Tweet objects to be processed
   */
  public void process(List<Tweet> tweets);

}
