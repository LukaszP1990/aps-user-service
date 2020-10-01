package com.advanced.protection.systems.multisensor.userservice.core.util;

import java.util.Date;

import com.advanced.protection.systems.multisensor.modelservice.constant.DataType;
import com.advanced.protection.systems.multisensor.modelservice.constant.SensorType;
import com.advanced.protection.systems.multisensor.modelservice.dto.DataDto;
import com.advanced.protection.systems.multisensor.modelservice.dto.SensorDto;
import com.advanced.protection.systems.multisensor.modelservice.param.DataParam;

public class DataUtil {

	private static final String POSITION_SENSOR_NAME = "position-sensor-name";
	private static final String RF_SENSOR_NAME = "rf-sensor-name";
	public static final String CLIENT_IP = "192.168.10.10";


	public static DataDto createPositionDataDto() {
		return DataDto.builder()
				.targetId(1L)
				.lat(1)
				.lon(1)
				.altitude(1d)
				.timeAdded(DateUtil.getRegularDate())
				.sensorDto(createSensorDto(POSITION_SENSOR_NAME))
				.clientIpAddress(CLIENT_IP)
				.dataType(DataType.POSITION)
				.build();
	}

	public static DataDto createRfDataDto() {
		return DataDto.builder()
				.targetId(1L)
				.middleFrequency(1)
				.rssi(1)
				.altitude(1d)
				.timeAdded(DateUtil.getRegularDate())
				.sensorDto(createSensorDto(RF_SENSOR_NAME))
				.clientIpAddress(CLIENT_IP)
				.dataType(DataType.RF)
				.build();
	}

	public static DataParam getDataParam(Date date) {
		return DataParam.builder()
				.lon(1)
				.lat(1)
				.timeAddedFrom(date)
				.timeAddedTo(DateUtil.getRegularDateTo())
				.rssi(1)
				.sensorName("sensor-name")
				.build();
	}

	private static SensorDto createSensorDto(String sensorName){
		return SensorDto.builder()
				.sensorType(SensorType.POSITION)
				.name(sensorName)
				.configured(true)
				.build();
	}

}
