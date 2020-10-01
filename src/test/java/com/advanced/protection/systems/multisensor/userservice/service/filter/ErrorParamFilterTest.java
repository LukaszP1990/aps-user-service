package com.advanced.protection.systems.multisensor.userservice.service.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.advanced.protection.systems.multisensor.userservice.core.util.DateUtil;
import com.advanced.protection.systems.multisensor.userservice.core.util.ErrorUtil;

class ErrorParamFilterTest {

	@Test
	void areAllErrorRfDataParamShouldReturnTrueWhenDateIsSet() {
		assertTrue(ErrorParamFilter.areAllErrorRfDataParam(ErrorUtil.getErrorParam(DateUtil.getRegularDateFrom())));
	}

	@Test
	void areAllErrorRfDataParamShouldReturnTrueWhenDateIsNotSet() {
		assertFalse(ErrorParamFilter.areAllErrorRfDataParam(ErrorUtil.getErrorParam(null)));
	}

	@Test
	void areAllErrorPositionDataParamShouldReturnTrueWhenDateIsSet() {
		assertTrue(ErrorParamFilter.areAllErrorPositionDataParam(ErrorUtil.getErrorParam(DateUtil.getRegularDateFrom())));
	}

	@Test
	void areAllErrorPositionDataParamShouldReturnTrueWhenDateIsNotSet() {
		assertFalse(ErrorParamFilter.areAllErrorPositionDataParam(ErrorUtil.getErrorParam(null)));
	}
}