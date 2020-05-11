package org.interview.application.tweet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.interview.domain.author.Author;
import org.interview.domain.tweet.Tweet;
import org.interview.domain.tweet.TweetListTransformer;
import org.interview.domain.tweet.TweetProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * TweetProcessor writing a list of tweets to a CSV file
 *
 * (this implementation is just for the sake of example, thus it's very basic.
 * in a real-life case, I would better use OpenCSV or equivalent lib)
 *
 * @author Fabio Bozzo
 */
@Profile("file")
@Component
@Slf4j
@RequiredArgsConstructor
public class CsvFileWriterTweetProcessor implements TweetProcessor {

  private final TweetListTransformer tweetListTransformer;

  @Value("${output.path}")
  private String destinationPath;

  @Value("${output.filename}")
  private String fileName;

  /**
   * {@inheritDoc}
   *
   * This implementation writes a denormalized table of tweets and authors to a CSV file
   */
  @Override
  public void process(List<Tweet> tweets) {

    Path path = Paths.get(destinationPath + fileName);
    try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
      val authorsMap = tweetListTransformer.groupTweetsByAuthorSorted(tweets);
      writeCsvHeader(writer);
      authorsMap.forEach(((author, tweetsPerAuthor) -> tweetsPerAuthor.forEach(tweet -> writeCsvLine(writer, author, tweet))));
    } catch (IOException e) {
      log.error("Cannot write to path {}: {}", path, e);
    }
  }

  private void writeCsvHeader(BufferedWriter writer) throws IOException {
    writer.write("\"author_id\",\"author_creation_date\",\"author_name\",\"tweet_id\",\"tweet_creation_date\",\"tweet_text\"");
    writer.write(System.lineSeparator());
  }

  private void writeCsvLine(Writer writer, Author author, Tweet tweet) {
    try {
      String line = String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
          author.getUserId(),
          author.getCreationDate(),
          author.getName(),
          tweet.getMessageId(),
          tweet.getCreationDate(),
          tweet.getText()
      );
      writer.write(line);
      writer.write(System.lineSeparator());
    } catch (IOException e) {
      log.error("Cannot write line to file: ", e);
    }
  }


}
