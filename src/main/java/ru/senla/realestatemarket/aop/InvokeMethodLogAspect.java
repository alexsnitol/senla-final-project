package ru.senla.realestatemarket.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@Component
@Aspect
@Slf4j
public class InvokeMethodLogAspect {

    @Before("allRepositoryMethodsPointcut()")
    public void allRepositoryMethodsAdvice(JoinPoint joinPoint) {
        log.info("Called repository method " + joinPoint.getSignature().getName() + " in "
                + joinPoint.getTarget().getClass().getSimpleName()
                + " with args " + Arrays.toString(joinPoint.getArgs()));
    }

    @Before("allServiceMethodsPointcut()")
    public void allServiceMethodsAdvice(JoinPoint joinPoint) {
        log.info("Called service method " + joinPoint.getSignature().getName() + " in "
                + joinPoint.getTarget().getClass().getSimpleName()
                + " with args " + Arrays.toString(joinPoint.getArgs()));
    }

    @Before("allControllerMethodsPointcut()")
    public void allControllerMethodsAdvice(JoinPoint joinPoint) {
        log.info("Called controller method " + joinPoint.getSignature().getName() + " in "
                + joinPoint.getTarget().getClass().getSimpleName()
                + " with args " + Arrays.toString(joinPoint.getArgs()));
    }

    @Pointcut("within(ru.senla.realestatemarket.repo..*)")
    public void allRepositoryMethodsPointcut() {}

    @Pointcut("within(ru.senla.realestatemarket.service..*)")
    public void allServiceMethodsPointcut() {}

    @Pointcut("within(ru.senla.realestatemarket.controller..*)")
    public void allControllerMethodsPointcut() {}

}
