package com.sustav.service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.sustav.BaseFunctionalTest;
import com.sustav.exception.ErrorCode;
import com.sustav.exception.ServiceException;
import com.sustav.model.Joke;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class JokeServiceTest extends BaseFunctionalTest {

  private static final Integer JOKE_ID = 1;
  private static final String JOKE_TYPE = "type";
  private static final String JOKE_SETUP = "setup";
  private static final String JOKE_PUNCHLINE = "punchline";
  private static final String JOKE_URI_COUNT_1 = "/jokes?count=1";
  private static final String JOKE_URI_COUNT_101 = "/jokes?count=1";

  @Autowired
  private WebTestClient webTestClient;
  @MockBean
  private JokeService jokeService;

  @Test
  void getJokes_shouldReturnJokes_whenCountLess100() {
    var expectedJoke = mockJoke();

    when(jokeService.getJokes(anyInt()))
        .thenReturn(Flux.just(expectedJoke));

    webTestClient.get()
                 .uri(JOKE_URI_COUNT_1)
                 .exchange()
                 .expectStatus()
                 .isOk()
                 .expectBody()
                 .jsonPath("$[0].id")
                 .isEqualTo(JOKE_ID)
                 .jsonPath("$[0].type")
                 .isEqualTo(JOKE_TYPE)
                 .jsonPath("$[0].setup")
                 .isEqualTo(JOKE_SETUP)
                 .jsonPath("$[0].punchline")
                 .isEqualTo(JOKE_PUNCHLINE);

    StepVerifier.create(jokeService.getJokes(1))
                .expectNext(expectedJoke)
                .verifyComplete();
  }

  @Test
  void getJokes_shouldThrowException_whenCountGreaterThan100() {
    when(jokeService.getJokes(anyInt()))
        .thenReturn(Flux.error(ServiceException.from(ErrorCode.COUNT_EXCEED)));

    webTestClient.get()
                 .uri(JOKE_URI_COUNT_101)
                 .exchange()
                 .expectStatus()
                 .isBadRequest()
                 .expectBody()
                 .jsonPath("$.code")
                 .isEqualTo(400)
                 .jsonPath("$.message")
                 .isEqualTo(ErrorCode.COUNT_EXCEED.getMessage());

    StepVerifier.create(jokeService.getJokes(101))
                .expectError(ServiceException.class)
                .verify();
  }

  private Joke mockJoke() {
    return Joke.builder()
               .id(JOKE_ID)
               .type(JOKE_TYPE)
               .setup(JOKE_SETUP)
               .punchline(JOKE_PUNCHLINE)
               .build();
  }

}