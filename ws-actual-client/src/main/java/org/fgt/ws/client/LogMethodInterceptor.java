package org.fgt.ws.client;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogMethodInterceptor implements MethodInterceptor
{

   public LogMethodInterceptor()
   {
      super();
   }

   @Override
   public Object invoke(MethodInvocation methodInvocation) throws Throwable
   {
      StopWatch watch = new StopWatch();
      watch.start();
      try
      {
         logInvokingMethod(methodInvocation);
         return methodInvocation.proceed();
      }
      finally
      {
         watch.stop();
         logMethodTiming(methodInvocation, watch.getTime());
      }
   }

   private void logMethodTiming(MethodInvocation methodInvocation, long time)
   {
      final Logger LOG = LoggerFactory.getLogger(methodInvocation.getThis().getClass());
      if (LOG.isDebugEnabled())
      {
         LOG.debug("Method {}() took {}ms", methodInvocation.getMethod().getName(), time);
      }
   }

   private void logInvokingMethod(MethodInvocation methodInvocation)
   {
      final Logger LOG = LoggerFactory.getLogger(methodInvocation.getThis().getClass());
      if (LOG.isDebugEnabled())
      {
         Method method = methodInvocation.getMethod();
         Object[] args = methodInvocation.getArguments();
         LOG.debug("Invoking method={}(), with arguments={}", method.toString(), Arrays
            .toString(args));
      }
   }
}
