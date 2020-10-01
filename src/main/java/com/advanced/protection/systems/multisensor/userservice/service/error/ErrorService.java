package com.advanced.protection.systems.multisensor.userservice.service.error;

import com.advanced.protection.systems.multisensor.modelservice.dto.ErrorDto;
import com.advanced.protection.systems.multisensor.modelservice.param.ErrorParam;
import com.advanced.protection.systems.multisensor.userservice.feignclient.FeignPositionClient;
import com.advanced.protection.systems.multisensor.userservice.feignclient.FeignRfClient;
import com.advanced.protection.systems.multisensor.userservice.service.filter.ErrorParamFilter;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Comparator;

@Component
@Slf4j
public class ErrorService {

    private final FeignRfClient feignRfClient;
    private final FeignPositionClient feignPositionClient;

    public ErrorService(FeignRfClient feignRfClient,
                        FeignPositionClient feignPositionClient) {
        this.feignRfClient = feignRfClient;
        this.feignPositionClient = feignPositionClient;
    }

    public Flux<ErrorDto> getErrors(ErrorParam errorParam) {
        log.info("get errors by errorParam: {} ", errorParam);
        return Try.of(() -> errorParam)
                .map(this::getErrorByParam)
                .getOrNull();
    }

    private Flux<ErrorDto> getErrorByParam(ErrorParam errorParam) {
        var fluxOfErrorDtoRfDataByParams = getErrorRfDataDtoByParams(errorParam);
        var fluxOfErrorDtoPositionDataByParams = getErrorPositionDataDtoByParams(errorParam);

        return fluxOfErrorDtoRfDataByParams.mergeWith(fluxOfErrorDtoPositionDataByParams)
                .sort(Comparator.comparing(ErrorDto::getTimeAdded));
    }

    private Flux<ErrorDto> getErrorRfDataDtoByParams(ErrorParam errorParam) {
        return ErrorParamFilter.areAllErrorRfDataParam(errorParam) ?
                feignRfClient.getErrors(errorParam.getType(), errorParam.getTimeAddedFrom(), errorParam.getTimeAddedTo()) :
                Flux.empty();
    }

    private Flux<ErrorDto> getErrorPositionDataDtoByParams(ErrorParam errorParam) {
        return ErrorParamFilter.areAllErrorPositionDataParam(errorParam) ?
                feignPositionClient.getErrors(errorParam.getType(), errorParam.getTimeAddedFrom(), errorParam.getTimeAddedTo()) :
                Flux.empty();
    }

}
