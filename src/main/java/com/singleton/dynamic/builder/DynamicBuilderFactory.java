package com.singleton.dynamic.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.singleton.dynamic.builder.annotation.Not;
import com.singleton.dynamic.builder.defaults.DefaultProvider;
import com.singleton.dynamic.builder.validation.NotParameterValidator;

public class DynamicBuilderFactory
{
    public <T> T createBuilderForClass(Class<T> clazz)
    {
        InvocationHandler handler = new BuilderInvocationHandler();

        @SuppressWarnings("unchecked")
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, handler);
        return proxy;
    }

    private class BuilderInvocationHandler implements InvocationHandler
    {
        private final Map<String, Object> valueMap = new HashMap<String, Object>();

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            if (!method.getName().equals("build") && args != null && args.length == 1)
            {
                Object parameterValue = args[0];
                performValidation(method, parameterValue);
                valueMap.put(method.getName(), parameterValue);
                return proxy;
            }
            else if (method.getName().equals("build") && args == null)
            {
                Class<?> returnClass = method.getReturnType();
                InvocationHandler handler = new BuiltObjectInvocationHandler(valueMap);
                return Proxy.newProxyInstance(returnClass.getClassLoader(), new Class<?>[] { returnClass }, handler);
            }

            System.out.println("Invalid method invoked " + method.getName());
            return null;
        }

        private void performValidation(Method method, Object parameterValue)
        {
            // pull off the annotations for the first parameter, given that
            for (Annotation singleAnnotation : method.getParameterAnnotations()[0])
            {
                if (singleAnnotation.annotationType().equals(Not.class))
                {
                    for (NotParameterValidator singleValidator : ((Not) singleAnnotation).value())
                    {
                        singleValidator.validate(parameterValue, method);
                    }
                }
            }
        }
    }

    private class BuiltObjectInvocationHandler implements InvocationHandler
    {
        private final DefaultProvider defaultProvider = new DefaultProvider();
        private final Map<String, Object> valueMap;

        public BuiltObjectInvocationHandler(Map<String, Object> valueMap)
        {
            this.valueMap = convertToGetterMethod(valueMap);
        }

        private Map<String, Object> convertToGetterMethod(Map<String, Object> originalMap)
        {
            Map<String, Object> convertedMap = new HashMap<String, Object>();
            for (String methodKey : originalMap.keySet())
            {
                String firstCharacter = methodKey.substring(0, 1).toUpperCase();
                String newMethodName = "get" + firstCharacter + methodKey.substring(1);
                convertedMap.put(newMethodName, originalMap.get(methodKey));
            }
            return convertedMap;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            if (valueMap.containsKey(method.getName()))
            {
                return valueMap.get(method.getName());
            }

            return defaultProvider.getDefaultValue(method);
        }
    }
}
