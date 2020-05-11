package org.interview.application.tweet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import java.io.IOException;
import java.util.List;
import org.interview.domain.tweet.Tweet;
import org.interview.utils.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class ThresholdTwitterStreamClientTest {

  private ThresholdTwitterStreamClient testee;

  private static final String JSON_RESPONSE =
      "{\"id\":1257616272402989000,\"created_at\":\"Tue May 05 10:20:59 +0000 2020\",\"text\":\"tweet-1\",\"user\":{\"id\":1209409606876684300,\"created_at\":\"Tue Dec 24 09:45:11 +0000 2019\",\"name\":\"author-1\",\"screen_name\":\"Author 1\"}}"
          + "\n{\"id\":1257616272402989001,\"created_at\":\"Tue May 05 10:21:59 +0000 2020\",\"text\":\"tweet-2\",\"user\":{\"id\":1209409606876684301,\"created_at\":\"Tue Dec 24 09:45:12 +0000 2019\",\"name\":\"author-2\",\"screen_name\":\"Author 2\"}}"
          + "\n{\"id\":1257616272402989002,\"created_at\":\"Tue May 05 10:22:59 +0000 2020\",\"text\":\"tweet-3\",\"user\":{\"id\":1209409606876684300,\"created_at\":\"Tue Dec 24 09:45:11 +0000 2019\",\"name\":\"author-1\",\"screen_name\":\"Author 1\"}}";

  @Before
  public void setUp() {

    testee = new ThresholdTwitterStreamClient(
      mockHttpRequestFactory(),
      mockObjectMapper()
    );

    ReflectionTestUtils.setField(testee, "endpointUrl", "http://localhost");
    ReflectionTestUtils.setField(testee, "tweetsCountThreshold", 2L);
    ReflectionTestUtils.setField(testee, "tweetsTimeThreshold", 30000L);
  }

  @Test
  public void readByFilterKeyword() {

    List<Tweet> tweets = testee.readByFilterKeyword("bieber");

    assertNotNull(tweets);
    assertEquals(2, tweets.size());
    assertEquals("tweet-1", tweets.get(0).getText());
    assertEquals("author-1", tweets.get(0).getAuthor().getName());
    assertEquals("tweet-2", tweets.get(1).getText());
    assertEquals("author-2", tweets.get(1).getAuthor().getName());
  }

  private HttpRequestFactory mockHttpRequestFactory() {
    HttpTransport transport = new MockHttpTransport() {
      @Override
      public LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
        return new MockLowLevelHttpRequest() {
          @Override
          public LowLevelHttpResponse execute() throws IOException {
            MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
            response.setStatusCode(200);
            response.setContentType(Json.MEDIA_TYPE);
            response.setContent(JSON_RESPONSE);
            return response;
          }
        };
      }
    };
    return transport.createRequestFactory();
  }

  private ObjectMapper mockObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setDateFormat(DateTimeUtils.twitterDateFormat());
    return mapper;
  }
}