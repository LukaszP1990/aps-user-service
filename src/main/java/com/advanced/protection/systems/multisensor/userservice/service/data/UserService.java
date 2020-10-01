package com.advanced.protection.systems.multisensor.userservice.service.data;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

import java.util.Comparator;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.advanced.protection.systems.multisensor.modelservice.constant.DataType;
import com.advanced.protection.systems.multisensor.modelservice.dto.DataDto;
import com.advanced.protection.systems.multisensor.modelservice.param.DataParam;
import com.advanced.protection.systems.multisensor.userservice.feignclient.FeignPositionClient;
import com.advanced.protection.systems.multisensor.userservice.feignclient.FeignRfClient;
import com.advanced.protection.systems.multisensor.userservice.service.filter.DataParamFilter;
import com.advanced.protection.systems.multisensor.userservice.core.config.kafka.producer.KafkaUserProducer;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;

@Slf4j
@Component
class UserService {

	private static final String RF_DATA = "rf-data-topic";
	private static final String POSITION_DATA = "position-data-topic";
	private final FeignRfClient feignRfClient;
	private final FeignPositionClient feignPositionClient;
	private final KafkaUserProducer kafkaUserProducer;

	UserService(final FeignRfClient feignRfClient,
				final FeignPositionClient feignPositionClient,
				final KafkaUserProducer kafkaUserProducer) {
		this.feignRfClient = feignRfClient;
		this.feignPositionClient = feignPositionClient;
		this.kafkaUserProducer = kafkaUserProducer;
	}

	Flux<DataDto> getData(DataParam dataParam) {
		log.info("get datas by dataParam: {} ", dataParam);
		return Try.of(() -> dataParam)
				.map(this::getDataByParam)
				.getOrNull();
	}

	Mono<DataDto> saveData(DataDto dataDto) {
		return Match(dataDto.getDataType()).of(
				Case($(DataType.RF), () -> saveRfData(dataDto)),
				Case($(DataType.POSITION), () -> savePositionData(dataDto)));
	}

	private Flux<DataDto> getDataByParam(DataParam dataParam) {
		var fluxOfRfDataDtoByParams = getRfDataDtoByParams(dataParam);
		var fluxOfPositionDataDtoDtoByParams = getPositionDataDtoByParams(dataParam);

		return fluxOfRfDataDtoByParams.mergeWith(fluxOfPositionDataDtoDtoByParams)
				.sort(Comparator.comparing(DataDto::getTimeAdded));
	}

	private Flux<DataDto> getRfDataDtoByParams(DataParam dataParam) {
		return DataParamFilter.areAllRfDataParam(dataParam) ?
				feignRfClient.getRfDatas(dataParam.getRssi(), dataParam.getTimeAddedFrom(), dataParam.getTimeAddedTo(), dataParam.getSensorName()) :
				Flux.empty();
	}

	private Flux<DataDto> getPositionDataDtoByParams(DataParam dataParam) {
		return DataParamFilter.areAllPositionDataParam(dataParam) ?
				feignPositionClient.getPositionDatas(dataParam.getLon(), dataParam.getLat(), dataParam.getTimeAddedFrom(), dataParam.getTimeAddedTo(), dataParam.getSensorName()) :
				Flux.empty();
	}

	private Mono<DataDto> saveRfData(DataDto dataDto) {
		log.info("save rfData: {} ", dataDto);
		return Mono.justOrEmpty(dataDto)
				.filter(Objects::nonNull)
				.flatMap(data -> kafkaUserProducer.sendMessages(data, RF_DATA))
				.map(senderResult -> {
					kafkaUserProducer.close();
					return senderResult;
				})
				.flatMap(senderResult -> Mono.just(dataDto));
	}

	private Mono<DataDto> savePositionData(DataDto dataDto) {
		log.info("save rfData: {} ", dataDto);
		return Mono.justOrEmpty(dataDto)
				.filter(Objects::nonNull)
				.flatMap(data -> kafkaUserProducer.sendMessages(data, POSITION_DATA))
				.map(senderResult -> {
					kafkaUserProducer.close();
					return senderResult;
				})
				.flatMap(senderResult -> Mono.just(dataDto));
	}

}
