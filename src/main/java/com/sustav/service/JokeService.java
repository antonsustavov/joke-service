package com.sustav.service;

import static com.sustav.exception.ErrorCode.COUNT_EXCEED;
import static com.sustav.exception.ErrorCode.EXTERNAL_SERVER_ERROR;

import com.sustav.configuration.properties.JokeProperties;
import com.sustav.exception.ServiceException;
import com.sustav.model.Joke;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

@Service
public record JokeService(WebClient webClient, JokeProperties properties) {

  public Flux<Joke> getJokes(int jokesCount) {
    return Flux.range(1, jokesCount)
               .handle(this::checkMaxJokesCount)
               .buffer(properties.getBatch())
               .flatMap(group -> Flux.fromIterable(group).flatMap(this::callExternalService));
  }

  private void checkMaxJokesCount(Integer i, SynchronousSink<Object> sink) {
    if (i <= properties.getMaxCountPerRequest()) {
      sink.next(i);
    } else {
      sink.error(ServiceException.from(COUNT_EXCEED));
    }
  }

  private Mono<Joke> callExternalService(Object group) {
    return webClient
        .get()
        .uri(properties.getRandomJokeUri())
        .retrieve()
        .onStatus(
            HttpStatusCode::is4xxClientError,
            clientResponse -> Mono.error(
                ServiceException.from(EXTERNAL_SERVER_ERROR)))
        .bodyToMono(Joke.class);
  }

}
