package com.advanced.protection.systems.multisensor.userservice.core.util;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class ServerResponseUtil {

    public static <T> Mono<ServerResponse> ok(T body) {
        return ServerResponse
                .ok()
                .body(BodyInserters.fromObject(body));
    }

    public static <T> Mono<ServerResponse> badRequest(T body) {
        return ServerResponse
                .badRequest()
                .body(BodyInserters.fromObject(body));
    }
}
