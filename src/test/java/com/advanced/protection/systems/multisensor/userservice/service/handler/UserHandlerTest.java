package com.advanced.protection.systems.multisensor.userservice.service.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.advanced.protection.systems.multisensor.modelservice.dto.DataDto;
import com.advanced.protection.systems.multisensor.userservice.AbstractIntegrationTest;
import com.advanced.protection.systems.multisensor.userservice.core.util.DataUtil;
import com.advanced.protection.systems.multisensor.userservice.core.util.DateUtil;
import com.advanced.protection.systems.multisensor.userservice.feignclient.FeignPositionClient;
import com.advanced.protection.systems.multisensor.userservice.feignclient.FeignRfClient;

import reactor.core.publisher.Flux;

class UserHandlerTest extends AbstractIntegrationTest {

	@MockBean
	private FeignRfClient feignRfClient;

	@MockBean
	private FeignPositionClient feignPositionClient;

	@Test
	void shouldGetData() {
		var dataParam = DataUtil.getDataParam(DateUtil.getRegularDateFrom());
		when(feignRfClient.getRfDatas(anyDouble(), any(), any(), anyString()))
				.thenReturn(Flux.just(DataUtil.createRfDataDto()));

		when(feignPositionClient.getPositionDatas(anyDouble(), anyDouble(), any(), any(), anyString()))
				.thenReturn(Flux.just(DataUtil.createPositionDataDto()));

		webTestClient.get().
				uri(uriBuilder ->
						uriBuilder
								.path("/api/user/data")
								.queryParam("lon", dataParam.getLon())
								.queryParam("lat", dataParam.getLat())
								.queryParam("timeAddedFrom", dataParam.getTimeAddedFrom())
								.queryParam("timeAddedTo", dataParam.getTimeAddedTo())
								.queryParam("rssi", dataParam.getRssi())
								.queryParam("sensorName", dataParam.getSensorName())
								.build())
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(DataDto.class)
				.hasSize(4);
	}

	@Test
	void saveData() {
	}
}