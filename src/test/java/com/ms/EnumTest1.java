package com.ms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;

import org.junit.Test;

public class EnumTest1 {

	public enum CalcType {
		CALC_A(value -> value),
		CALC_B(value -> value * 10),
		CALC_C(value -> value * 3),
		CALC_ETC(value -> 0)
		;
		
		// enum에 정의된 funciton
		private Function<Integer, Integer> expression;
		
		// constructor
		CalcType(Function<Integer, Integer> expression) {
			this.expression = expression;
		}
		
		// 계산 적용
		public int calculate(int value) {
			return expression.apply(value);
		}
	}
	
	@Test
	void calTest() {
		//given
		CalcType calcType = CalcType.CALC_C;
		
		//when
		int value = 4;
		int result = calcType.calculate(value);
		
		//then
		assertEquals(12, result);
	}
	
}
