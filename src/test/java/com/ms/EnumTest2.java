package com.ms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumTest2 {
	
	enum PayType {
		// 열거형 변수들
		CASH("현금", List.of("REMITTANCE", "ACCOUNT TRANSFER"))
		,CARD("카드", List.of("CREDIT", "NAVER"))
		,EMPTY("없음", Collections.emptyList()); // 또는 Collections.EMPTY_LIST = 비어 있는 리스트
		
		// field
		private String title;
		private List<String> payMethodList;
		
		// constructor
		PayType(String title, List<String> payMethodList) {
			this.title = title;
			this.payMethodList = payMethodList;
		}
		
		// 결제 수단(payMethod) String이 해당 payType의 payMethodList 안에 존재하는지 확인
		private boolean hasPayMethod(String payMethod) {
			return payMethodList.stream() // pay 리스트 순회
					.anyMatch(pay -> pay.equals(payMethod)); // 하나라도 있으면 true
		}
		
		// payMethod으로 payType 찾기
		public static PayType findByPayMethod(String payMethod) {
			return Arrays.stream(PayType.values()) // PayType의 열거형 변수들을 stream으로 변환
					.filter(payType -> payType.hasPayMethod(payMethod)) // payMethod를 포함하고 있는 payType을 뽑아냄
					.findAny() // findAny = 찾은 것들 중 랜덤하게 하나 뽑기 (findFirst 등 가장 처음 걸 들고 오는 것도 있음)
					.orElse(EMPTY); // orElse = 아무것도 없을 경우
		}
		
	}
	
	
	@Test
	void payTest() {
		// given
		String payMethod = "NAVER";
		
		// when
		PayType p = PayType.findByPayMethod(payMethod);
		
		// then
		assertEquals(p, PayType.CARD);
	}
	
}
