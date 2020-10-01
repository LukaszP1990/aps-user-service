package com.advanced.protection.systems.multisensor.userservice.service.handler;

import com.advanced.protection.systems.multisensor.modelservice.constant.ErrorType;
import com.advanced.protection.systems.multisensor.modelservice.param.ErrorParam;
import com.advanced.protection.systems.multisensor.userservice.core.util.ServerResponseUtil;
import com.advanced.protection.systems.multisensor.userservice.service.error.ErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ErrorHandler {

    private final ErrorService errorService;

    public ErrorHandler(final ErrorService errorService) {
        this.errorService = errorService;
    }

    public Mono<ServerResponse> getErrors(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ErrorParam.class)
                .flatMap(errorParam -> errorService.getErrors(errorParam).collectList())
                .filter(errorDtos -> !errorDtos.isEmpty())
                .flatMap(ServerResponseUtil::ok)
                .switchIfEmpty(Mono.defer(this::getBadRequest));
    }

    private Mono<ServerResponse> getBadRequest() {
        log.error("error while getting RfData: {}", ErrorType.NOT_FOUND_ERROR.getText());
        return ServerResponseUtil.badRequest(ErrorType.NOT_FOUND_ERROR);
    }
}
