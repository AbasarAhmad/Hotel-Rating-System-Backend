package com.saar.serviceregistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceReqistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceReqistoryApplication.class, args);
	}

}
