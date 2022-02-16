package com.tofazzal.notification.scheduler;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class EmployeeWebClient {
    WebClient client = WebClient.create("http://localhost:8080");

    public void get(){
        Flux<Request> employeeFlux = client.get()
                .uri("/requests")
                .retrieve()
                .bodyToFlux(Request.class);

        employeeFlux.subscribe(System.out::println);
    }
}
