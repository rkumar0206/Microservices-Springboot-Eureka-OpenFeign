package com.rtb.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.rtb.app.entity.Student;
import com.rtb.app.repository.StudentRepository;
import com.rtb.app.request.CreateStudentRequest;
import com.rtb.app.response.StudentResponse;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	WebClient addressWebClient;

	@Autowired
	CommonService commonService;
	
	private Logger logger = LoggerFactory.getLogger(StudentService.class);
	
	public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {

		Student student = new Student();
		student.setFirstName(createStudentRequest.getFirstName());
		student.setLastName(createStudentRequest.getLastName());
		student.setEmail(createStudentRequest.getEmail());

		student.setAddressId(createStudentRequest.getAddressId());
		student = studentRepository.save(student);

		StudentResponse studentResponse = new StudentResponse(student);

		// studentResponse.setAddress(getAddressById(student.getAddressId()));

		/*
		 * as the we are using resilience4j, and it uses aop internally we cannot have
		 * the getAddressById method in this service as aop will not able to make the
		 * proxy in the same class, so we have defined the method in in separate service
		 * class called CommonService
		 */
		studentResponse.setAddress(commonService.getAddressById(student.getAddressId()));

		return studentResponse;
	}

	public StudentResponse getById(long id) {
		
		logger.info("Inside student getById()");

		Student student = studentRepository.findById(id).get();

		StudentResponse studentResponse = new StudentResponse(student);

		// studentResponse.setAddress(getAddressById(student.getAddressId()));

		studentResponse.setAddress(commonService.getAddressById(student.getAddressId()));

		return studentResponse;
	}
	
	
	// using web flux or web client
//	public AddressResponse getAddressById(Long addressId) {
//
//		Mono<AddressResponse> addressResponse = addressWebClient
//				.get().uri("/getById/" + addressId).retrieve()
//				.bodyToMono(AddressResponse.class);
//
//		return addressResponse.block();
//	}

}
