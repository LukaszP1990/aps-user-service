package com.advanced.protection.systems.multisensor.userservice.service.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.advanced.protection.systems.multisensor.modelservice.dto.ErrorDto;
import com.advanced.protection.systems.multisensor.userservice.AbstractIntegrationTest;
import com.advanced.protection.systems.multisensor.userservice.core.util.DateUtil;
import com.advanced.protection.systems.multisensor.userservice.core.util.ErrorUtil;
import com.advanced.protection.systems.multisensor.userservice.feignclient.FeignPositionClient;
import com.advanced.protection.systems.multisensor.userservice.feignclient.FeignRfClient;

import reactor.core.publisher.Flux;

class ErrorHandlerTest extends AbstractIntegrationTest {

	@MockBean
	private FeignRfClient feignRfClient;

	@MockBean
	private FeignPositionClient feignPositionClient;

	@Test
	void getErrors() {
		var errorParam = ErrorUtil.getErrorParam(DateUtil.getRegularDateFrom());
		when(feignRfClient.getErrors(anyString(), any(), any()))
				.thenReturn(Flux.just(ErrorUtil.createErrorDto()));

		when(feignPositionClient.getErrors(anyString(), any(), any()))
				.thenReturn(Flux.just(ErrorUtil.createErrorDto()));

		webTestClient.get().
				uri(uriBuilder ->
						uriBuilder
								.path("/api/user/data/error")
								.queryParam("type", errorParam.getType())
								.queryParam("timeAddedFrom", errorParam.getTimeAddedFrom())
								.queryParam("timeAddedTo", errorParam.getTimeAddedTo())
								.build())
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(ErrorDto.class)
				.hasSize(4);
	}

}