package com.advanced.protection.systems.multisensor.userservice.webui.rest;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

import com.advanced.protection.systems.multisensor.userservice.service.handler.ErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.advanced.protection.systems.multisensor.userservice.service.handler.UserHandler;

@Configuration
public class UserRouting {

	private static final String PATH = "/api/user/data";
	private static final String ERROR = "/error";

	@Bean
	public RouterFunction<ServerResponse> userDataRoutes(UserHandler handler) {
		return RouterFunctions
				.route(GET(PATH), handler::getData)
				.andRoute(POST(PATH), handler::saveData);
	}

	@Bean
	public RouterFunction<ServerResponse> errorRoutes(ErrorHandler handler) {
		return RouterFunctions
				.route(GET(PATH.concat(ERROR)), handler::getErrors);
	}
}
