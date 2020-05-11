package org.interview.application.tweet;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.interview.domain.author.Author;
import org.interview.domain.tweet.Tweet;
import org.interview.domain.tweet.TweetListTransformer;
import org.springframework.stereotype.Component;

/**
 * TweetListTransformer Stream API implementation
 *
 * @author Fabio Bozzo
 */
@Component
public class StreamTweetListTransformer implements TweetListTransformer {

  @Override
  public LinkedHashMap<Author, List<Tweet>> groupTweetsByAuthorSorted(List<Tweet> tweets) {

    Comparator<Tweet> authorComparator = Comparator.comparing(t -> t.getAuthor().getCreationDate());
    Comparator<Tweet> tweetComparator = authorComparator.thenComparing(t -> t.getCreationDate());

//    TODO: verify actual requirements!
//    Comparator<Tweet> tweetComparator = Comparator.comparing(t -> t.getCreationDate());

    return tweets.stream()
        .sorted(tweetComparator)
        .collect(Collectors.groupingBy(Tweet::getAuthor, LinkedHashMap::new, Collectors.toList()));
  }
}
