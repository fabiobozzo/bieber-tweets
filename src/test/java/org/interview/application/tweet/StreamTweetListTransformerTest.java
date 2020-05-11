package org.interview.application.tweet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.interview.domain.author.Author;
import org.interview.domain.tweet.Tweet;
import org.junit.jupiter.api.Test;

public class StreamTweetListTransformerTest {

  @Test
  public void groupTweetsByAuthorSorted() {

    var testee = new StreamTweetListTransformer();
    Map<Author, List<Tweet>> result = testee.groupTweetsByAuthorSorted(inputData());

    assertNotNull(result);
    assertEquals(3, result.size());

    assertEquals(
        "t-5,t-3,t-2,t-4,t-1",
        result.entrySet().stream()
          .map(Entry::getValue)
          .flatMap(List::stream)
          .map(Tweet::getMessageId)
          .collect(Collectors.joining(","))
    );
  }

  private List<Tweet> inputData() {

    Date date1 = Date.from(LocalDateTime.parse("2020-05-04T00:00:00").atZone(ZoneId.systemDefault()).toInstant());
    Date date2 = Date.from(LocalDateTime.parse("2020-05-04T00:01:00").atZone(ZoneId.systemDefault()).toInstant());
    Date date3 = Date.from(LocalDateTime.parse("2020-05-04T00:02:00").atZone(ZoneId.systemDefault()).toInstant());
    Date date4 = Date.from(LocalDateTime.parse("2020-05-04T00:03:00").atZone(ZoneId.systemDefault()).toInstant());
    Date date5 = Date.from(LocalDateTime.parse("2020-05-04T00:04:00").atZone(ZoneId.systemDefault()).toInstant());

    return Arrays.asList(
      Tweet.builder()
          .messageId("t-1")
          .creationDate(date4)
          .author(
              Author.builder().userId("a-1").creationDate(date3).build()
          )
          .build(),
        Tweet.builder()
            .messageId("t-2")
            .creationDate(date3)
            .author(
                Author.builder().userId("a-2").creationDate(date2).build()
            )
            .build(),
        Tweet.builder()
            .messageId("t-3")
            .creationDate(date5)
            .author(
                Author.builder().userId("a-3").creationDate(date1).build()
            )
            .build(),
        Tweet.builder()
            .messageId("t-4")
            .creationDate(date1)
            .author(
                Author.builder().userId("a-1").creationDate(date3).build()
            )
            .build(),
        Tweet.builder()
            .messageId("t-5")
            .creationDate(date2)
            .author(
                Author.builder().userId("a-3").creationDate(date1).build()
            )
            .build()
    );
  }

}