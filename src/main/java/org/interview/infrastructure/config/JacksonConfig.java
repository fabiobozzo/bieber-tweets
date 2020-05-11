package org.interview.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.interview.utils.DateTimeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for Jackson ObjectMapper
 *
 * @author Fabio Bozzo
 */
@Configuration
public class JacksonConfig {

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setDateFormat(DateTimeUtils.twitterDateFormat());
    return mapper;
  }
}
