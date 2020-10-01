package com.advanced.protection.systems.multisensor.userservice.core.util;

import java.util.Date;

import com.advanced.protection.systems.multisensor.modelservice.constant.ErrorType;
import com.advanced.protection.systems.multisensor.modelservice.dto.ErrorDto;
import com.advanced.protection.systems.multisensor.modelservice.param.ErrorParam;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorUtil {

	public static ErrorDto createErrorDto() {
		return ErrorDto.builder()
				.text(ErrorType.DATA_SAVE_ERROR.getText())
				.timeAdded(DateUtil.getRegularDate())
				.build();
	}

	public static ErrorParam getErrorParam(Date date) {
		return ErrorParam.builder()
				.timeAddedFrom(date)
				.timeAddedTo(DateUtil.getRegularDateTo())
				.type(ErrorType.DATA_SAVE_ERROR.getText())
				.build();
	}
}
