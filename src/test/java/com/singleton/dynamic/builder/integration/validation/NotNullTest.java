package com.singleton.dynamic.builder.integration.validation;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.Not;
import static com.singleton.dynamic.builder.annotation.Validator.*;

/**
 * Test class to validate that an argument to a builder method can be declared as not null and it
 * will be enforced.
 * 
 * @author Dustin Singleton
 */
public class NotNullTest
{
    private final DynamicBuilderFactory factory = new DynamicBuilderFactory();

    @Test(expected = IllegalArgumentException.class)
    public void testNotNull_nullValue()
    {
        factory.createBuilderForClass(NotNullBuilder.class).stringValue(null);
    }

    private interface NotNullBuilder
    {

        NotNullBuilder stringValue(@Not({ NULL }) String value);

        NotNullBuiltObject build();
    }

    private interface NotNullBuiltObject
    {
        String getStringValue();
    }
}
