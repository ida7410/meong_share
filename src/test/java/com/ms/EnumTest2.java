package com.ms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumTest2 {
	
	@Getter
	enum PayMethod {
		// 열거형
		REMITTANCE("무통장입금")
		,ACCOUNT_TRANSFER("계좌이체")
		,CREDIT("신용카드")
		,NAVER("네이버페이");
		
		// field
		private String title;
		
		// constructor
		PayMethod(String title) {
			this.title = title;
		}
	}
	
	enum PayType {
		// 열거형 변수들
		CASH("현금", List.of(PayMethod.REMITTANCE, PayMethod.ACCOUNT_TRANSFER))
		,CARD("카드", List.of(PayMethod.CREDIT, PayMethod.NAVER))
		,EMPTY("없음", Collections.emptyList()); // 또는 Collections.EMPTY_LIST = 비어 있는 리스트
		
		// field
		private String title;
		private List<PayMethod> payMethodList;
		
		// constructor
		PayType(String title, List<PayMethod> payMethodList) {
			this.title = title;
			this.payMethodList = payMethodList;
		}
		
		// 결제 수단(payMethod) String이 해당 payType의 payMethodList 안에 존재하는지 확인
		private boolean hasPayMethod(PayMethod payMethod) {
			return payMethodList.stream() // pay 리스트 순회
					.anyMatch(pay -> pay.equals(payMethod)); // 하나라도 있으면 true
		}
		
		// payMethod으로 payType 찾기
		public static PayType findByPayMethod(PayMethod payMethod) {
			return Arrays.stream(PayType.values()) // PayType의 열거형 변수들을 stream으로 변환
					.filter(payType -> payType.hasPayMethod(payMethod)) // payMethod를 포함하고 있는 payType을 뽑아냄
					.findAny() // findAny = 찾은 것들 중 랜덤하게 하나 뽑기 (findFirst 등 가장 처음 걸 들고 오는 것도 있음)
					.orElse(EMPTY); // orElse = 아무것도 없을 경우
		}
		
	}
	
	
//	@Test
//	void payTest() {
//		// given
//		String payMethod = "NAVER";
//		
//		// when
//		PayType p = PayType.findByPayMethod(payMethod);
//		
//		// then
//		assertEquals(p, PayType.CARD);
//	}
	
	@Test
	void payTest2() {
		// given
		PayMethod payMethod = PayMethod.ACCOUNT_TRANSFER;
		
		// when
		PayType p = PayType.findByPayMethod(payMethod);
		
		// then
		assertEquals(p, PayType.CASH);
		assertEquals(payMethod.getTitle(), "계좌이체");
	}
	
}
