package com.singleton.dynamic.builder.integration.validation;

import static com.singleton.dynamic.builder.validation.NotParameterValidator.EMPTY;
import static com.singleton.dynamic.builder.validation.NotParameterValidator.NULL;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.Not;

/**
 * <p>
 * Test class to ensure that {@link Not} works with empty and {@code null}.
 * </p>
 *
 * @author Dustin Singleton
 */
@SuppressWarnings("javadoc")
public class NotNullOrEmptyTest
{
    private final DynamicBuilderFactory factory = new DynamicBuilderFactory();

    @Test(expected = IllegalArgumentException.class)
    public void testNotNull_nullValue()
    {
        factory.createBuilderForClass(NotNullOrEmptyBuilder.class).stringValue(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNotNull_emptyValue()
    {
        factory.createBuilderForClass(NotNullOrEmptyBuilder.class).stringValue("");
    }

    private interface NotNullOrEmptyBuilder
    {
        NotNullOrEmptyBuilder stringValue(@Not({ NULL, EMPTY }) String value);

        NotNullOrEmptyBuiltObject build();
    }

    private interface NotNullOrEmptyBuiltObject
    {
        String getStringValue();
    }
}
