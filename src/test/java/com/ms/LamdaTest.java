package com.ms;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LamdaTest {
	
//	@Test
	void lamdaTest1() {
		List<String> fruits = List.of("apple", "orange", "peach");
		fruits
		.stream()
		.filter(fruit -> fruit.startsWith("p"))
		.forEach(fruit -> log.info("%%%%%% {}", fruit));
	}
	
	@Test
	void lamdaTest2() {
		List<String> fruits = List.of("apple", "orange", "peach");
		fruits = fruits
		.stream()
		.map(fruit -> fruit.toUpperCase())
		.collect(Collectors.toList()); // stream to list
		
		log.info(fruits.toString());
	}
	
//	@Test
	void methodReference() {
		List<String> fruits = List.of("apple", "orange", "peach");
		fruits = fruits
				.stream()
				.map(String::toUpperCase)
				.collect(Collectors.toList());
		
		log.info(fruits.toString());
	}
	
}
