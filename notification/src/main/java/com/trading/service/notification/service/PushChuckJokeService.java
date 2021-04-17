package com.trading.service.notification.service;


import com.trading.service.notification.NotificationApplication;
import com.trading.service.notification.model.IcndbJoke;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.trading.service.notification.client.FcmClient;
@Service
public class PushChuckJokeService {

  private final FcmClient fcmClient;

  private final WebClient webClient;

  private int seq = 0;

  public PushChuckJokeService(FcmClient fcmClient, WebClient webClient) {
    this.fcmClient = fcmClient;
    this.webClient = webClient;
  }

  @Scheduled(fixedDelay = 30_000)
  public void sendChuckQuotes() {
    IcndbJoke joke = this.webClient.get().uri("http://api.icndb.com/jokes/random")
        .retrieve().bodyToMono(IcndbJoke.class).block();
    try {
      sendPushMessage(joke);
    }
    catch (InterruptedException | ExecutionException e) {
      NotificationApplication.logger.error("send chuck joke", e);
    }
  }

  void sendPushMessage(IcndbJoke joke) throws InterruptedException, ExecutionException {
    Map<String, String> data = new HashMap<>();
    data.put("id", String.valueOf(joke.getValue().getId()));
    data.put("joke", joke.getValue().getJoke());
    data.put("seq", String.valueOf(this.seq++));
    data.put("ts", String.valueOf(System.currentTimeMillis()));

    System.out.println("Sending chuck joke...");
    this.fcmClient.send(data);
  }

}
