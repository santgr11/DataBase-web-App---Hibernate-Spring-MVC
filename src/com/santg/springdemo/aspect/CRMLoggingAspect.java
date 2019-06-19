package com.santg.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

	// setup logger
	private Logger logger = Logger.getLogger(getClass().getName());
	
	// setup pointcut declarations
	@Pointcut("execution(* com.santg.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.santg.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.santg.springdemo.dao.*.*(..))")
	private void forDAOPackage() {}

	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {}
	
	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		
		// display method we are calling
		logger.info("===> in @Before: calling method: " + joinPoint.getSignature().toShortString());
		
		// display the arguments to the method
		
		
		// get the arguments
		Object[] args = joinPoint.getArgs();
		
		// loop thru and display the args
		for (Object tempArg: args) {
			logger.info("===> argument: " + tempArg);
		}
		
	}
	
	// add @AfterReturning advice
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		
		// display method we are returning from
		logger.info("===> in @AfterReturning: from method: " + joinPoint.getSignature().toShortString());

		// display data returned
		logger.info("===> result: " + result);
	}
}
