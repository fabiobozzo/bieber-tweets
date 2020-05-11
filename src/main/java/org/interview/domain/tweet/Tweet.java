package org.interview.domain.tweet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.interview.domain.author.Author;

/**
 * Entity for mapping a Twitter API Json tweet
 *
 * @author Fabio Bozzo
 */
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {

  @EqualsAndHashCode.Include
  @JsonProperty(TweetFieldNames.MESSAGE_ID)
  private String messageId;

  @JsonProperty(TweetFieldNames.CREATED_AT)
  private Date creationDate;

  @JsonProperty(TweetFieldNames.TEXT)
  private String text;

  @JsonProperty(TweetFieldNames.AUTHOR)
  private Author author;
}
