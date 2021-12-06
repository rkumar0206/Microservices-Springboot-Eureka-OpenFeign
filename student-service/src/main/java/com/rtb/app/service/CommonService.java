package com.rtb.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtb.app.feignclients.AddressFeignClient;
import com.rtb.app.response.AddressResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CommonService {
	
	private Logger logger= LoggerFactory.getLogger(CommonService.class);
	
	private int count = 1;
	
	@Autowired
	AddressFeignClient addressFeignClient;
	
	/*
	 * This is the circuit breaker method and will be called by the student service to fetch the address
	 * 
	 * Conditions in which this method will be called : 
	 * - when the state is closed - this method will be called and for any reason if it fails then
	 * the fallback  method will be called and the dummy response will be returned
	 * 
	 * - when the state is opened - this method will never be called and the fallback method 
	 * will be directly called
	 * 
	 * 
	 * Use case : 
	 * 
	 * the resilience4j circuit method should only be used when the response is not so important.
	 * if say we have a service called payment service, then we should never use the circuit breaker
	 * as providing a default/dummy response will be not good for our application, payment is mandatory.
	 * so in that case a failure response should go the consumer
	 */
	@CircuitBreaker(
			name = "addressService", // same as defined in application.properties
			fallbackMethod = "fallbackGetAddressById" //  this method will be called when the call to address is failed 
			)
	public AddressResponse getAddressById(Long addressId) {

		logger.info("count = " + count);
		count++;
		
		return addressFeignClient.getById(addressId);
	}
	
	/*
	 * This is the fallback method and its signature should be exactly same
	 * as the circuit breaker method
	 */
	public AddressResponse fallbackGetAddressById(Long addressId, Throwable th) {
		
		logger.error("Error : " + th);
		
		// it is the dummy response and will be returned when 
		// the call to the address is failed
		return new AddressResponse();
	}

}
