package com.advanced.protection.systems.multisensor.userservice.service.data;

import org.springframework.stereotype.Component;

import com.advanced.protection.systems.multisensor.modelservice.dto.DataDto;
import com.advanced.protection.systems.multisensor.modelservice.param.DataParam;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserServiceFacadeImpl implements UserServiceFacade{

	private final UserService userService;

	public UserServiceFacadeImpl(final UserService userService) {
		this.userService = userService;
	}

	@Override
	public Flux<DataDto> getData(DataParam dataParam) {
		return userService.getData(dataParam);
	}

	@Override
	public Mono<DataDto> saveData(DataDto dataDto) {
		return userService.saveData(dataDto);
	}
}
