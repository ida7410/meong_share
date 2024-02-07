package com.ms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

//@SpringBootTest // 서버를 켤 때 콘솔 뜨는 것들 = spring boot를 동작시키는 annotation
@Slf4j
class MeongShareApplicationTests {

	@Test // 아래 메소드가 테스트로 진행 = 주석 처리하면 실행x
	void contextLoads() {
		log.info("##### 테스트");
		assertEquals(20, 10 + 20);
		
		if (ObjectUtils.isEmpty(log)) { // null인지 list나 string으로 테스트
			
		}
	}

}
