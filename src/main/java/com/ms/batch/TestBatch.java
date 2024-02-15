package com.ms.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TestBatch {
	
	// job
	@Scheduled(cron = "0 */1 * * * *") // 실행 주기는 여기 (별개)
	public void task() {
		log.info("####### batch 수행");
	}
	
}
