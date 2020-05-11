package org.interview.domain.tweet;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.interview.domain.author.Author;

public interface TweetListTransformer {

  /**
   * Generate a Map whose key is an Author and the value is a author's Tweets list
   *
   * @param tweets The list of Tweet objects to be grouped
   */
  public LinkedHashMap<Author, List<Tweet>> groupTweetsByAuthorSorted(List<Tweet> tweets);
}
