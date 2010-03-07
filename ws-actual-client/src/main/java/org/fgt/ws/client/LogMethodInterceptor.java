package org.fgt.ws.client;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogMethodInterceptor implements MethodInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(LogMethodInterceptor.class);
    private StopWatch watch;

    public LogMethodInterceptor() {
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        startTime();
        try {
            logInvokingMethod(methodInvocation);
            return methodInvocation.proceed();
        } finally {
            endTime();
            logMethodTiming(methodInvocation);
        }
    }

    private void endTime() {
        this.watch.stop();
    }

    private void startTime() {
        this.watch = new StopWatch();
        this.watch.start();
    }

    private void logMethodTiming(MethodInvocation methodInvocation) {
        if (LOG.isDebugEnabled()) {
            long time = watch.getTime();
            LOG.debug("Method {}() took {}ms", methodInvocation.getMethod().getName(),time);
        }
    }

    private void logInvokingMethod(MethodInvocation methodInvocation) {
        if (LOG.isDebugEnabled()) {
            Method method = methodInvocation.getMethod();
            Object[] args = methodInvocation.getArguments();
            LOG.debug("Invoking method={}(), with arguments={}", method.toString(),
                    Arrays.toString(args));
        }
    }
}
