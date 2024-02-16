package com.learnmicroserive.notificationservice;

import com.learnmicroserive.notificationservice.model.EmployeeResponse;
import com.learnmicroserive.notificationservice.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@RestController
@EnableBinding(Sink.class)
public class NotificationserviceApplication {

	private Logger logger = LoggerFactory.getLogger(NotificationserviceApplication.class);

	@Autowired
	private WebClient.Builder webClient;

	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;


	@StreamListener(Sink.INPUT)
	public void consumeMessage(Message message){
		EmployeeResponse employeeResponse = circuitBreakerFactory.create("getEmployee").run(
				() -> {
					return webClient.build().get()
						.uri("http://localhost:9002/api/v1/employees/" + message.getEmployeeId())
						.retrieve().bodyToMono(EmployeeResponse.class).block();
				}, throwable -> {
					EmployeeResponse response = new EmployeeResponse();
					response.setFirstName("Anonymous");
					response.setLastName("Employee");
					return response;
				}
		);
		if(employeeResponse != null){
			logger.info("Consume payload : "+ employeeResponse.getFirstName() + " " + employeeResponse.getLastName()
					+ " " + message.getMessage());
		}
	}



	public static void main(String[] args) {
		SpringApplication.run(NotificationserviceApplication.class, args);
	}

}
