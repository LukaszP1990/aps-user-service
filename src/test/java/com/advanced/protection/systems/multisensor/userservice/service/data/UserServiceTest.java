package com.advanced.protection.systems.multisensor.userservice.service.data;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.advanced.protection.systems.multisensor.userservice.core.config.kafka.producer.KafkaUserProducer;
import com.advanced.protection.systems.multisensor.userservice.core.util.DataUtil;
import com.advanced.protection.systems.multisensor.userservice.core.util.DateUtil;
import com.advanced.protection.systems.multisensor.userservice.feignclient.FeignPositionClient;
import com.advanced.protection.systems.multisensor.userservice.feignclient.FeignRfClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

	private final FeignRfClient feignRfClient = mock(FeignRfClient.class);
	private final FeignPositionClient feignPositionClient = mock(FeignPositionClient.class);
	private final KafkaUserProducer kafkaUserProducer = mock(KafkaUserProducer.class);
	private UserService userService = new UserService(feignRfClient, feignPositionClient, kafkaUserProducer);

	@Test
	void shouldGetData() {
		when(feignRfClient.getRfDatas(anyDouble(), any(), any(), anyString()))
				.thenReturn(Flux.just(DataUtil.createRfDataDto()));

		when(feignPositionClient.getPositionDatas(anyDouble(), anyDouble(), any(), any(), anyString()))
				.thenReturn(Flux.just(DataUtil.createPositionDataDto()));

		StepVerifier.create(userService.getData(DataUtil.getDataParam(DateUtil.getRegularDateFrom())))
				.expectNextCount(2)
				.verifyComplete();
	}

	@Test
	void saveData() {
		when(kafkaUserProducer.sendMessages(any(), anyString()))
				.thenReturn(Mono.empty());

		StepVerifier.create(userService.saveData(DataUtil.createPositionDataDto()))
				.expectSubscription()
				.verifyComplete();
	}

}