package com.ms.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Component // singleton = 프로젝트 통틀어 new를 딱 한 번만 부름
@Aspect // 부가 기능 정의(advise) + 어디에 적용(pointcut)
@Slf4j
public class TimeTraceOAop {
	
	// 1) 수행할 패키지 지정
//	@Around("execution(* com.ms..*(..))")
	
	// 2) Annotation 지정 = 내가 annotation을 붙인 method에게만 동작
	@Around("@annotation(TimeTrace)") // TimeTrace annotation이 붙어 있을 때만 수행
	 public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		 // 타겟이 적용하는 메소드 - joinPoint
		 
		 // 시간 측정
		 StopWatch sw = new StopWatch();
		 sw.start();
		 
		 // 본래 할 일 수행 (예: 회원가입, 로그인, 글쓰기 etc)
		 Object prodceedObj = joinPoint.proceed();
		 
		 sw.stop();
		 log.info("##### 실행 시간(ms): " + sw.getTotalTimeMillis());
		 log.info("$$$$$ 실행 시간(ms): {}", sw.prettyPrint());
		 
		 return prodceedObj;
	 }
	
}
