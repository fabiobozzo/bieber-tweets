package org.interview.domain.tweet;

import java.util.List;

public interface TwitterStreamClient {

  /**
   * Read realtime statuses from the Twitter Streaming API and return Tweets
   *
   * @param filterKeyword The keyword to filter the statuses on
   * @return a List of Tweet objects
   */
  public List<Tweet> readByFilterKeyword(String filterKeyword);

}
