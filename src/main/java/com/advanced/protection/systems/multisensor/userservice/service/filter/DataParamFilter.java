package com.advanced.protection.systems.multisensor.userservice.service.filter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.advanced.protection.systems.multisensor.modelservice.param.DataParam;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataParamFilter {

	public static boolean areAllRfDataParam(DataParam dataParam) {
		var rfDataFields = Stream.of(
				dataParam.getRssi(),
				dataParam.getTimeAddedFrom(),
				dataParam.getTimeAddedTo(),
				dataParam.getSensorName()
		).collect(Collectors.toList());
		return validateFields(rfDataFields);
	}

	public static boolean areAllPositionDataParam(DataParam dataParam) {
		var positionDataFields = Stream.of(
				dataParam.getLon(),
				dataParam.getLat(),
				dataParam.getTimeAddedFrom(),
				dataParam.getTimeAddedTo(),
				dataParam.getSensorName()
		).collect(Collectors.toList());
		return validateFields(positionDataFields);
	}

	private static <T> boolean validateFields(List<T> dataParams) {
		return Optional.ofNullable(dataParams)
				.map(field -> areAllField(dataParams))
				.orElse(false);
	}

	private static <T> boolean areAllField(List<T> dataParams) {
		return IntStream.rangeClosed(0, dataParams.size() - 1)
				.allMatch(value -> Objects.nonNull(dataParams.get(value)));
	}
}
