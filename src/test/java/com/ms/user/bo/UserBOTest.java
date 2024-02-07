package com.ms.user.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class UserBOTest {
	
	@Autowired
	private UserBO userBO;

	@Test
	@Transactional // import springframework로 = insert하고 지워줌 = 롤백
	void test() {
		userBO.addUser("idd", "sss", "test", "ttt", "xxxx", "xxx");
	}

}
