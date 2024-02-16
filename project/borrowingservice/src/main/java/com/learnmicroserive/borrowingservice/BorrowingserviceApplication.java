package com.learnmicroserive.borrowingservice;

import com.learnmicroserive.borrowingservice.config.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import({AxonConfig.class})
public class BorrowingserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BorrowingserviceApplication.class, args);
	}
}
