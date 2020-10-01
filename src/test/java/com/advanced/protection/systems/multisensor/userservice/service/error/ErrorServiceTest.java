package com.advanced.protection.systems.multisensor.userservice.service.error;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.advanced.protection.systems.multisensor.userservice.core.util.DateUtil;
import com.advanced.protection.systems.multisensor.userservice.core.util.ErrorUtil;
import com.advanced.protection.systems.multisensor.userservice.feignclient.FeignPositionClient;
import com.advanced.protection.systems.multisensor.userservice.feignclient.FeignRfClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ErrorServiceTest {

	private final FeignRfClient feignRfClient = mock(FeignRfClient.class);
	private final FeignPositionClient feignPositionClient = mock(FeignPositionClient.class);
	private ErrorService errorService = new ErrorService(feignRfClient, feignPositionClient);

	@Test
	void shouldGetErrors() {
		when(feignRfClient.getErrors(anyString(), any(), any()))
				.thenReturn(Flux.just(ErrorUtil.createErrorDto()));

		when(feignPositionClient.getErrors(anyString(), any(), any()))
				.thenReturn(Flux.just(ErrorUtil.createErrorDto()));

		StepVerifier.create(errorService.getErrors(ErrorUtil.getErrorParam(DateUtil.getRegularDateFrom())))
				.expectNextCount(2)
				.verifyComplete();
	}
}