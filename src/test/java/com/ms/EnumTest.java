package com.ms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lombok.Getter;

public class EnumTest {
	
	@Getter
	public enum Status {
		// 열거형 정의
		Y(1, true),
		N(0, false)
		;
		
		// enum에 정의된 항목의 field
		private int value1;
		private boolean value2;
		
		// constructor
		Status(int value1, boolean value2) {
			this.value1 = value1;
			this.value2 = value2;
		}
	}
	
	@Test
	void statusTest() {
		// given = prepare
		Status status = Status.Y;
		
		// when = execute
		int value1 = status.getValue1();
		boolean value2 = status.isValue2();
		
		// then = check
		assertEquals(status, Status.Y);
		assertEquals(value1, 1);
		assertEquals(value2, true);
	}
	
}
