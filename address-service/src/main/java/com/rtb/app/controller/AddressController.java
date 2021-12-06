package com.rtb.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtb.app.request.CreateAddressRequest;
import com.rtb.app.response.AddressResponse;
import com.rtb.app.service.AddressService;

@RestController
@RequestMapping("/api/address")
@RefreshScope  // will be used for refreshing the @Value value
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@Value("${address.test}")
	private String test;
	
	@PostMapping("/create")
	public AddressResponse createAddress(@RequestBody CreateAddressRequest createAddressRequest) {
		
		return addressService.createAddress(createAddressRequest);
	}
	
	@GetMapping("getById/{id}")
	public AddressResponse getById(@PathVariable Long id) {
		
		return addressService.getById(id);
	}
	
	@GetMapping("/test")
	public String test() {
		
		return test;
	}
	
}
