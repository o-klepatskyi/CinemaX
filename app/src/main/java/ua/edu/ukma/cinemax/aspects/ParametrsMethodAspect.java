package ua.edu.ukma.cinemax.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;


@Aspect
@Component
public class ParametrsMethodAspect {
    static final Logger LOGGER = LoggerFactory.getLogger(ParametrsMethodAspect.class);

    @Around("@annotation(ua.edu.ukma.cinemax.aspects.ParametersMethod)")
    public Object logTimeMethod(ProceedingJoinPoint point) throws Throwable {
        String startParams = Arrays.stream(point.getArgs()).map(Object::toString).collect(Collectors.joining(","));
        Object resultMethod = point.proceed();
        LOGGER.info("Method: " + point.getSignature().getName() + "; Start params: " + startParams);
        return resultMethod;
    }

    @AfterReturning(value = "@annotation(ua.edu.ukma.cinemax.aspects.ParametersMethod)", returning = "result")
    public void logAfter(JoinPoint point, Object result) {
        LOGGER.info("Method: " + point.getSignature().getName() + "; End params: " + result);
    }
}
