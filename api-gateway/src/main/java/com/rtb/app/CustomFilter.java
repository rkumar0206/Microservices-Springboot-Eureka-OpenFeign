package com.rtb.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Configuration
public class CustomFilter implements GlobalFilter {
	
	Logger logger = LoggerFactory.getLogger(CustomFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		// pre-filter
		/*
		 * This method will be called before every request,
		 * We can do various operations here, for example: Authentication
		 * Let's say if the authentication is failed, then we can directly
		 * throw an error from here only and will not call the microservice
		 */
		
		ServerHttpRequest request = exchange.getRequest();
		
		if (request.getURI().toString().contains("/api/student/")) {
			
			// here you can do something with the specific microservice
			// here, student microservice
		}
		
		logger.info("Authorization : " + request.getHeaders().getFirst("Authorization"));
		
		// uncomment if you don't want post-filter
		//return chain.filter(exchange);
		
		
		// this will call the appropriate microservice 
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			
			
			// post filter
			
			/*
			 * This will be called just after our 
			 * microservice give response, and before our api-gateway 
			 * gives the response to the consumer
			 */
			
			ServerHttpResponse response = exchange.getResponse();
			
			logger.info("Response Status code : " + response.getStatusCode());
			
		}));
	}
	
	
}
