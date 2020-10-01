package com.advanced.protection.systems.multisensor.userservice.service.filter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.advanced.protection.systems.multisensor.userservice.core.util.DataUtil;
import com.advanced.protection.systems.multisensor.userservice.core.util.DateUtil;

class DataParamFilterTest {

	@Test
	void areAllRfDataParamShouldReturnTrueWhenDateIsSet() {
		assertTrue(DataParamFilter.areAllRfDataParam(DataUtil.getDataParam(DateUtil.getRegularDateFrom())));
	}

	@Test
	void areAllRfDataParamShouldReturnFalseWhenDateIsNotSet() {
		assertFalse(DataParamFilter.areAllRfDataParam(DataUtil.getDataParam(null)));
	}

	@Test
	void areAllPositionDataParamShouldReturnTrueWhenDateIsSet() {
		assertTrue(DataParamFilter.areAllPositionDataParam(DataUtil.getDataParam(DateUtil.getRegularDateFrom())));
	}

	@Test
	void areAllPositionDataParamShouldReturnFalseWhenDateIsNotSet() {
		assertFalse(DataParamFilter.areAllPositionDataParam(DataUtil.getDataParam(null)));
	}
}