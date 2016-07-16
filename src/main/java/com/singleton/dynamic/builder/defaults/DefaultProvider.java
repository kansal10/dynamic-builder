package com.singleton.dynamic.builder.defaults;

import java.lang.reflect.Method;

/**
 * API to provide default values based on the provided method.
 * 
 * @author Dustin Singleton
 */
public class DefaultProvider
{
    private static final String BOOLEAN = "boolean";
    private static final String CHAR = "char";
    private static final String DOUBLE = "double";
    private static final String FLOAT = "float";
    private static final String LONG = "long";
    private static final String INT = "int";
    private static final String SHORT = "short";

    /**
     * Gets the default value for the method specified. While this <strong>might</strong> be the java default value, it
     * does not necessarily mean that it <strong>must</strong> be the java default. Other aspects of the method (such as
     * annotations) could dictate that a different default value should be used.
     * 
     * @param method
     *            The method that the default value should be retrieved for.
     *
     * @return the default value for the method specified.
     */
    public Object getDefaultValue(Method method)
    {
        Class<?> returnType = method.getReturnType();
        if (returnType.isPrimitive())
        {
            return getPrimitiveDefaultValue(returnType);
        }

        return null;
    }

    private static Object getPrimitiveDefaultValue(Class<?> returnType)
    {
        String returnTypeName = returnType.getName();

        if (INT.equals(returnTypeName))
        {
            return 0;
        }
        else if (LONG.equals(returnTypeName))
        {
            return 0L;
        }
        else if (BOOLEAN.equals(returnTypeName))
        {
            return false;
        }
        else if (DOUBLE.equals(returnTypeName))
        {
            return 0.0;
        }
        else if (SHORT.equals(returnTypeName))
        {
            return (short) 0;
        }
        else if (FLOAT.equals(returnTypeName))
        {
            return 0.0f;
        }
        else if (CHAR.equals(returnTypeName))
        {
            return '\u0000';
        }
        else
        {
            // Return byte, given that it is the only other primitive type
            return (byte) 0;
        }
    }
}
