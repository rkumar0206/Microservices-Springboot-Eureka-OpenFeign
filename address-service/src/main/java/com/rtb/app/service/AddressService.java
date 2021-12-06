package com.rtb.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rtb.app.entity.Address;
import com.rtb.app.repository.AddressRepository;
import com.rtb.app.request.CreateAddressRequest;
import com.rtb.app.response.AddressResponse;

@Service
public class AddressService {

	Logger logger = LoggerFactory.getLogger(AddressService.class);

	@Autowired
	AddressRepository addressRepository;

	public AddressResponse createAddress(CreateAddressRequest createAddressRequest) {

		Address address = new Address();
		address.setStreet(createAddressRequest.getStreet());
		address.setCity(createAddressRequest.getCity());

		addressRepository.save(address);

		return new AddressResponse(address);
	}

	public AddressResponse getById(Long id) {

		logger.info("Inside getById " + id);
		
		return new AddressResponse(addressRepository.findById(id).get());
	}
}
