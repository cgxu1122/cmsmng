package com.ifhz.core.base.aop;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AopLog {

    Logger logger = LoggerFactory.getLogger(AopLog.class);

    private void writeThrowLog(JoinPoint point, Throwable throwable) {
        logger.info("afterThrowing params {}|throw {} | method {} | args {}", new Object[]{point, throwable, point.getSignature().getName(), point.getArgs()});
    }

    public void afterReturning(JoinPoint point, Object arg0) throws Throwable {
        logger.info("afterReturning params {}| return {} | method {} | args {}", new Object[]{point, arg0, point.getSignature().getName(), point.getArgs()});
    }

    public void before(JoinPoint point)
            throws Throwable {
        logger.info("before params {} | method {} | args {}", new Object[]{point, point.getSignature().getName(), point.getArgs()});
    }
}
