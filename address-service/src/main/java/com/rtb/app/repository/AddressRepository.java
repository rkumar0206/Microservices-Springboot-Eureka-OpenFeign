package com.rtb.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtb.app.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
