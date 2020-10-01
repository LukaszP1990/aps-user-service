package com.advanced.protection.systems.multisensor.userservice.service.filter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.advanced.protection.systems.multisensor.modelservice.param.ErrorParam;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorParamFilter {

    public static boolean areAllErrorRfDataParam(ErrorParam errorParam) {
        var errorRfDataFields = Stream.of(
                errorParam.getType(),
                errorParam.getTimeAddedFrom(),
                errorParam.getTimeAddedTo()
        ).collect(Collectors.toList());
        return validateFields(errorRfDataFields);
    }

    public static boolean areAllErrorPositionDataParam(ErrorParam errorParam) {
        var errorPositionDataFields = Stream.of(
                errorParam.getType(),
                errorParam.getTimeAddedFrom(),
                errorParam.getTimeAddedTo()
        ).collect(Collectors.toList());
        return validateFields(errorPositionDataFields);
    }

    private static <T> boolean validateFields(List<T> errorParam) {
        return Optional.ofNullable(errorParam)
                .map(field -> areAllFields(errorParam))
                .orElse(false);
    }

    private static <T> boolean areAllFields(List<T> errorParam) {
        return IntStream.rangeClosed(0, errorParam.size() - 1)
                .allMatch(value -> Objects.nonNull(errorParam.get(value)));
    }}
