package com.sustav.configuration;

import com.sustav.configuration.properties.JokeProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CommonConfiguration {

  @Bean
  public WebClient webClient(JokeProperties properties) {
    return WebClient.create(properties.getUrl());
  }

}
