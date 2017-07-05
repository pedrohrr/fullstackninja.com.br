package br.com.fullstackninja.h2dbspringdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class H2dbSpringDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(H2dbSpringDataApplication.class, args);
	}

}