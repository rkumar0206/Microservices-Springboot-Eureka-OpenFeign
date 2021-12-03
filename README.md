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
