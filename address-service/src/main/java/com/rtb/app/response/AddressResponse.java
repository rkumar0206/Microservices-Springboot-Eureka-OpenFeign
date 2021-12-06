package com.rtb.app.response;

import com.rtb.app.entity.Address;

public class AddressResponse {

	private Long addressId;
	private String street;
	private String city;
	
	public AddressResponse(Address address) {
		
		this.addressId = address.getId();
		this.street = address.getStreet();
		this.city = address.getCity();
	}
	
	
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
