package com.singleton.dynamic.builder.defaults;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;

/**
 * Test class to ensure that primitives values (when not set) will still the
 * java default value.
 * 
 * @author Dustin Singleton
 */
public class PrimitiveValuesDefaultsTest
{

    private final DynamicBuilderFactory factory = new DynamicBuilderFactory();

    @Test
    public void testIntDefaultValues()
    {
        IntBuilder builder = factory.createBuilderForClass(IntBuilder.class);

        assertThat(builder.build().getIntValue(), is(0));
    }

    // TODO: add additional tests for the other types

    private interface IntBuilder
    {
        IntBuilder intValue(int intValue);

        IntBuiltObject build();
    }

    private interface IntBuiltObject
    {
        int getIntValue();
    }
}
