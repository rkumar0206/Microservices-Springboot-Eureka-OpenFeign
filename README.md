# Microservices-Springboot-Eureka-OpenFeign
Microservices using spring boot, open feign, eureka server, and many more

## Monolithic Applications :
- In monolith applications all the services are included in a single application.
- For Example : Let's consider an University Application, Now in university there will many components or services like, student, professor, examination, admission, etc.  
- So, in monolithic application all this sercices will be present in a single application.
- Disadvantages:
  - We cannot scale our server for a specific service, like, when there are addmissions going on, then the admission service will be loaded and need to be upgraded, we cannot do it in the monolithic application as we will need to update the server for all the services.
  - If one service is down, all entire application will be down.
  - we cannot use all this services independently.
  

## Microservice  (micro + service => Small services (APIs)): 

- Microservice is nothing but a collection of independent APIs (Services).
- All the services run independently without affecting the entire application.
- Scaling a specific service is very much easier and convinient. Like in above example if the addmission service has more traffic then we can only scale the admission service and not others.

## Eureka Sercer : 

- consider a scenario where you have many microservices, now you updated the port of microservice-1, therefore you need to update the port in all other microservices which are dependent on the microservice-1. This can be a hectic task.
- Now, Eureka server helps us fix this problem, it takes care of the changes you have made in one microservice to be updated in all other microservices.

## OpenFleign : 
- Open flient is an application which makes it very easy for microservices to communicate with each other.

---

## Making a project

Let's work on a project and see how microservices work.
- We will make two microservices, _address-service_ and _student-service_.

## Address-Service

### STEP 1 : Make a new Springboot project

Dependencies : 
- data-jpa
- spring-web
- mysql driver

#### pom.xml
```xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.6.1</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.rtb.cloud</groupId>
	<artifactId>address-service</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>address-service</name>
	<description>address-service</description>
	<properties>
		<java.version>11</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>

```

### STEP 2 : application.properties

#### application.properties

```properties

spring.application.name=address-service
spring.datasource.url=jdbc:mysql://localhost:3306/student_database
spring.datasource.username=root
spring.datasource.password=12345

spring.jpa.show-sql=true

server.port=8082

```

### STEP 3 : Add an entity class for address

#### Address.java

```java

package com.rtb.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "street")
	private String street;

	@Column(name = "city")
	private String city;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


```

### STEP 4 : Make repository

```java
package com.rtb.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtb.app.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
```

### STEP 5 : Make one CreateAddressRequest class

- This will be used for receiving the request

#### CreateAddressRequest.java
```java
package com.rtb.app.request;

public class CreateAddressRequest {

	private String street;
	private String city;
	
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
```

### STEP 6 : Make one AddressResponse class

- This will be use for sending reponse

```java
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
```

### STEP 7 : Make Service class

#### AddressService.java

```java

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
```

### STEP 8 : Make controller class





