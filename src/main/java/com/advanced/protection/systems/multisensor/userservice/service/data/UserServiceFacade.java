package com.advanced.protection.systems.multisensor.userservice.service.data;

import com.advanced.protection.systems.multisensor.modelservice.dto.DataDto;
import com.advanced.protection.systems.multisensor.modelservice.param.DataParam;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserServiceFacade {

	Flux<DataDto> getData(DataParam dataParam);

	Mono<DataDto> saveData(DataDto dataDto);
}
