package com.advanced.protection.systems.multisensor.userservice.feignclient;

import java.util.Date;

import com.advanced.protection.systems.multisensor.modelservice.dto.ErrorDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.advanced.protection.systems.multisensor.modelservice.dto.DataDto;
import com.advanced.protection.systems.multisensor.userservice.core.constant.RfDataServiceConstant;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

@ReactiveFeignClient(name = RfDataServiceConstant.RF_DATA_RESOURCE)
public interface FeignRfClient {

	@GetMapping(path = "/rf-data")
	Flux<DataDto> getRfDatas(@RequestParam("rssi") double rssi,
							 @RequestParam("timeAddedFrom") Date timeAddedFrom,
							 @RequestParam("timeAddedTo") Date timeAddedTo,
							 @RequestParam("sensorName") String sensorName);

	@GetMapping(path = "/rf-data/errors")
	Flux<ErrorDto> getErrors(@RequestParam("type") String type,
							 @RequestParam("timeAddedFrom") Date timeAddedFrom,
							 @RequestParam("timeAddedTo") Date timeAddedTo);
}
