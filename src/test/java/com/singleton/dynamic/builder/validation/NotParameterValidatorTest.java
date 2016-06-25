package com.singleton.dynamic.builder.validation;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Test class for {@link NotParameterValidator}.
 * 
 * @author Dustin Singleton
 */
public class NotParameterValidatorTest
{
    private final Method method = InterfaceWithMethod.class.getMethods()[0];

    @Test
    public void testNotNull_nullValue()
    {
        try
        {
            NotParameterValidator.NULL.validate(null, method);
            fail("Expected IllegalArgumentException but none was thrown when null validation occurred with null value");
        }
        catch (IllegalArgumentException e)
        {
            assertThat(e.getMessage(), is(method.getName() + " was provided null, but non null values are required"));
        }
    }

    @Test
    public void testNotNull_nonNullValue()
    {
        NotParameterValidator.NULL.validate("non-null value", method);
    }

    @Test
    public void testNotNull_emptyValue()
    {
        NotParameterValidator.NULL.validate("", method);
    }

    @Test
    public void testNotEmpty_emptyStringValue()
    {
        try
        {
            NotParameterValidator.EMPTY.validate("", method);
            fail("Expected IllegalArgumentException but none was thrown when empty validation occurred with empty value");
        }
        catch (IllegalArgumentException e)
        {
            assertThat(e.getMessage(), is(method.getName() + " was provided empty, but non empty values are required"));
        }
    }

    @Test
    public void testNotEmpty_nonEmptyString()
    {
        NotParameterValidator.EMPTY.validate("non-empty-string", method);
    }

    @Test
    public void testNotEmpty_nullString()
    {
        NotParameterValidator.EMPTY.validate(null, method);
    }

    private interface InterfaceWithMethod
    {
        void method();
    }
}