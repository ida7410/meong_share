package com.ms;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.ToString;
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
	
//	@Test
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
	
	@ToString
	@AllArgsConstructor
	class Person {
		private String name;
		private int age;
		
		public void printInfo() {
			log.info("&&&&&&&&&" + this);
		}
	}
	
	@Test
	void lambdaMethodReference() {
		List<Person> personList = new ArrayList<>();
		personList.add(new Person("ida", 22));
		personList.add(new Person("woo", 18));
		
		// 객체 안에 있는 method 호출
		personList.forEach(person -> person.printInfo()); // lambda = (객체 이름 지정) -> (객체 이름).(객체 method)()
		personList.forEach(Person::printInfo); // method reference = 괄호 없이 = (객체 타입)::(객체 method)
		
		// 객체를 println으로 출력
		personList.forEach(person -> System.out.println(person));
		personList.forEach(System.out::println);
	}
	
}
