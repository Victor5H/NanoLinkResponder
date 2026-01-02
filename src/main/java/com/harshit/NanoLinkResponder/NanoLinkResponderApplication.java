package com.harshit.NanoLinkResponder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class NanoLinkResponderApplication {

	public static void main(String[] args) {
		SpringApplication.run(NanoLinkResponderApplication.class, args);
	}

}
