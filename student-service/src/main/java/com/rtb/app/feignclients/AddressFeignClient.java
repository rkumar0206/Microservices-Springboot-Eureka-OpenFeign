package com.rtb.app.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rtb.app.response.AddressResponse;

//using eureka server and api gateway
/*
 * Here we will call the address service using our api gateway
 * the request will first go to the api gateway, from there api gateway
 * will make the request to the address service and then will give the 
 * response to the student service
 * 
 * also the load balancing will be taken care by the api-gateway automatically
 * 
 * This Feign client is now not specific to only the address service, 
 * we can call any micro-service from here
 */
@FeignClient(
		value = "api-gateway" // application name of our gateway
		)
public interface AddressFeignClient {

	// here we just need to have the method signature
	@GetMapping("/address-service/api/address/getById/{id}")
	public AddressResponse getById(@PathVariable Long id);
	
}


// using eureka server
/*@FeignClient(
		// this should exactly match with the application name registered with the eureka server
		value = "address-service",
		path = "/api/address"
		)
public interface AddressFeignClient {

	// here we just need to have the method signature
	@GetMapping("getById/{id}")
	public AddressResponse getById(@PathVariable Long id);
	
}*/


// without using eureka server
/*@FeignClient(
		url = "${address.service.url}",
		value = "address-feign-cliet"
		//,path = "/api/address" // we can also provide the path, but here 
		)
public interface AddressFeignClient {

	// here we just need to have the method signature
	@GetMapping("getById/{id}")
	public AddressResponse getById(@PathVariable Long id);
	
}*/

