package com.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스케줄러
@SpringBootApplication
public class MeongShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeongShareApplication.class, args);
	}

}
