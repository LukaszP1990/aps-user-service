package com.advanced.protection.systems.multisensor.userservice.feignclient;

import java.util.Date;

import com.advanced.protection.systems.multisensor.modelservice.dto.DataDto;
import com.advanced.protection.systems.multisensor.modelservice.dto.ErrorDto;
import com.advanced.protection.systems.multisensor.userservice.core.constant.PositionDataServiceConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;

@ReactiveFeignClient(name = PositionDataServiceConstant.POSITION_DATA_RESOURCE)
public interface FeignPositionClient {

    @GetMapping(path = "/position-data")
    Flux<DataDto> getPositionDatas(@RequestParam("lon") double lon,
                                   @RequestParam("lat") double lat,
								   @RequestParam("timeAddedFrom") Date timeAddedFrom,
								   @RequestParam("timeAddedTo") Date timeAddedTo,
                                   @RequestParam("sensorName") String sensorName);

    @GetMapping(path = "/position-data/errors")
    Flux<ErrorDto> getErrors(@RequestParam("type") String type,
							 @RequestParam("timeAddedFrom") Date timeAddedFrom,
							 @RequestParam("timeAddedTo") Date timeAddedTo);
}
