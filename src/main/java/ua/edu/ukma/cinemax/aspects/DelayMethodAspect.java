package ua.edu.ukma.cinemax.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class DelayMethodAspect {
    static final Logger LOGGER = LoggerFactory.getLogger(DelayMethodAspect.class);

    @Around("@annotation(ua.edu.ukma.cinemax.aspects.DelayMethod)")
    public Object logTimeMethod(ProceedingJoinPoint point) throws Throwable {
        long startMethod = System.currentTimeMillis();
        Object resultMethod = point.proceed();
        long endMethod = System.currentTimeMillis();
        LOGGER.info("Method: " + point.getSignature().getName() + "; Delay: " + (endMethod - startMethod) + " millis");
        return resultMethod;
    }
}
