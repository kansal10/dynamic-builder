package com.singleton.dynamic.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.singleton.dynamic.builder.annotation.Not;
import com.singleton.dynamic.builder.validation.NotParameterValidator;

public class DynamicBuilderFactory
{
    public <T> T createBuilderForClass(Class<T> clazz)
    {
        InvocationHandler handler = new BuilderInvocationHandler();

        @SuppressWarnings("unchecked")
        T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz },
                handler);
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
                return Proxy.newProxyInstance(returnClass.getClassLoader(),
                        new Class<?>[] { returnClass }, handler);
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

            Class<?> returnType = method.getReturnType();
            if (returnType.isPrimitive())
            {
                return getPrimitiveDefaultValue(returnType);
            }

            return null;
        }

        private Object getPrimitiveDefaultValue(Class<?> returnType)
        {
            String returnTypeName = returnType.getName();

            if ("byte".equals(returnTypeName))
            {
                return (byte) 0;
            }
            else if ("short".equals(returnTypeName))
            {
                return (short) 0;
            }
            else if ("int".equals(returnTypeName))
            {
                return 0;
            }
            else if ("long".equals(returnTypeName))
            {
                return 0L;
            }
            else if ("float".equals(returnTypeName))
            {
                return 0.0f;
            }
            else if ("double".equals(returnTypeName))
            {
                return 0.0;
            }
            else if ("char".equals(returnTypeName))
            {
                return '\u0000';
            }
            else if ("boolean".equals(returnTypeName))
            {
                return false;
            }
            return null;
        }
    }
}
