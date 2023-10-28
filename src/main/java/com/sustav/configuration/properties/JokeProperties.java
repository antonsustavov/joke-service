package com.sustav.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "joke.property")
public class JokeProperties {

  private Integer maxCountPerRequest;
  private Integer batch;
  private String url;
  private String randomJokeUri;

}
