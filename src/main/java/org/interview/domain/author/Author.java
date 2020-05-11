package org.interview.domain.author;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity for mapping a Twitter API Json tweet's author
 *
 * @author Fabio Bozzo
 */
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {

  @EqualsAndHashCode.Include
  @JsonProperty(AuthorFieldNames.USER_ID)
  private String userId;

  @JsonProperty(AuthorFieldNames.CREATED_AT)
  private Date creationDate;

  @JsonProperty(AuthorFieldNames.NAME)
  private String name;

  @JsonProperty(AuthorFieldNames.SCREEN_NAME)
  private String screenName;
}
