package com.sustav.controller;

import com.sustav.model.Joke;
import com.sustav.service.JokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/jokes")
@RequiredArgsConstructor
public class JokeController {

  private final JokeService jokeService;

  @GetMapping
  public Flux<Joke> getRandomJokes(@RequestParam(required = false, defaultValue = "5") int count) {
    return jokeService.getJokes(count);
  }

}
