package com.advanced.protection.systems.multisensor.userservice.service.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.advanced.protection.systems.multisensor.modelservice.constant.ErrorType;
import com.advanced.protection.systems.multisensor.modelservice.dto.DataDto;
import com.advanced.protection.systems.multisensor.modelservice.param.DataParam;
import com.advanced.protection.systems.multisensor.userservice.core.util.ClientUtil;
import com.advanced.protection.systems.multisensor.userservice.core.util.ServerResponseUtil;
import com.advanced.protection.systems.multisensor.userservice.service.data.UserServiceFacade;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class UserHandler {

	private final UserServiceFacade userServiceFacade;

	public UserHandler(final UserServiceFacade userServiceFacade) {
		this.userServiceFacade = userServiceFacade;
	}

	public Mono<ServerResponse> getData(ServerRequest serverRequest) {
		return serverRequest.bodyToMono(DataParam.class)
				.flatMap(dataParam -> userServiceFacade.getData(dataParam).collectList())
				.filter(rfDataDtos -> !rfDataDtos.isEmpty())
				.flatMap(ServerResponseUtil::ok)
				.switchIfEmpty(Mono.defer(this::getBadRequest));
	}

	public Mono<ServerResponse> saveData(ServerRequest serverRequest) {
		var ipAddress = ClientUtil.getIpAddress(serverRequest);
		return serverRequest.bodyToMono(DataDto.class)
				.map(dataDto -> ClientUtil.setIpAddress(dataDto, ipAddress))
				.flatMap(userServiceFacade::saveData)
				.flatMap(ServerResponseUtil::ok)
				.switchIfEmpty(Mono.defer(() -> ServerResponseUtil.badRequest(ErrorType.DATA_SAVE_ERROR)));
	}

	private Mono<ServerResponse> getBadRequest() {
		log.error("error while getting RfData: {}", ErrorType.NOT_FOUND_DATA_ERROR.getText());
		return ServerResponseUtil.badRequest(ErrorType.NOT_FOUND_DATA_ERROR);
	}

}
