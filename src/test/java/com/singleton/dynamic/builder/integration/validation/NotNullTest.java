package com.singleton.dynamic.builder.integration.validation;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.NotNull;

/**
 * Test class to validate that an argument to a builder method can be declared as not null 
 * and it will be enforced.
 * 
 * @author Dustin Singleton
 */
public class NotNullTest
{
    @Test(expected = IllegalArgumentException.class)
    public void testNotNull_nullValue()
    {
        DynamicBuilderFactory factory = new DynamicBuilderFactory();
        NotNullBuilder builder = factory.createBuilderForClass(NotNullBuilder.class);
        
        builder.stringValue(null).build();
    }
    
    private interface NotNullBuilder
    {
        NotNullBuilder stringValue(@NotNull String value);
        
        NotNullBuiltObject build();
    }
    
    private interface NotNullBuiltObject 
    {
        String getStringValue();
    }
}
