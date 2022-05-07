package by.lobovich.delivery.util.logger;

import by.lobovich.delivery.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component

public class LoggingAspect {

    @Pointcut(value = "execution(* by.lobovich.delivery.service.impl.OrderServiceImpl.*(..))")
    public void callAtOrderServicePublic() {
    }

    @Pointcut(value = "execution(* by.lobovich.delivery.service.impl.OrderServiceImpl.getAllByUser(..))")
    public void callAtOrderServiceGetAll() {
    }

    @Before(value = "callAtOrderServicePublic()")
    public void beforeCallAtOrderServicePublic(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(" ,"));
        log.info("before {}, args=[{}]", jp.getSignature(), args);
    }

    @AfterReturning(value = "callAtOrderServicePublic()", returning = "result")
    public void afterReturningCallAt(JoinPoint jp, Order result) {
        log.info("method name: {}, return value: {}", jp.getSignature().getName(), result);
    }

    @AfterReturning(value = "callAtOrderServiceGetAll()", returning = "list")
    public void afterReturningCallAt(JoinPoint jp, List<Order> list) {
        log.info("method name: {}, list size: {}", jp.getSignature().getName(), list.size());
    }
}
