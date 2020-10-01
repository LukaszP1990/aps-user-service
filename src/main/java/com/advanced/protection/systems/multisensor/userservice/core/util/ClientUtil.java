package com.advanced.protection.systems.multisensor.userservice.core.util;

import java.util.Objects;

import org.springframework.web.reactive.function.server.ServerRequest;

import com.advanced.protection.systems.multisensor.modelservice.dto.DataDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientUtil {

	public static DataDto setIpAddress(DataDto dataDto,
									   String ipAddress) {
		dataDto.setClientIpAddress(ipAddress);
		return dataDto;
	}

	public static String getIpAddress(ServerRequest serverRequest) {
		return serverRequest.remoteAddress()
				.filter(inetSocketAddress -> Objects.nonNull(inetSocketAddress.getAddress().getHostAddress()))
				.map(inetSocketAddress -> inetSocketAddress.getAddress().getHostAddress())
				.orElse(null);
	}
}
