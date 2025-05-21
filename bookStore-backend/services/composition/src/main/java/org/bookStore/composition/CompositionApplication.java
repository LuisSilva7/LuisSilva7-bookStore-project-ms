package org.bookStore.composition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CompositionApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompositionApplication.class, args);
	}

}
