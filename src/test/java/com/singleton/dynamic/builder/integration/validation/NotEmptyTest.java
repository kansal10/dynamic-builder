package com.singleton.dynamic.builder.integration.validation;

import static com.singleton.dynamic.builder.validation.NotParameterValidator.EMPTY;
import static com.singleton.dynamic.builder.validation.NotParameterValidator.NULL;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;
import com.singleton.dynamic.builder.annotation.Not;

/**
 * <p>
 * Test to ensure that the not empty annotation works.
 * </p>
 *
 * @author Dustin Singleton
 */
@SuppressWarnings("javadoc")
public class NotEmptyTest
{
    private final DynamicBuilderFactory factory = new DynamicBuilderFactory();

    @Test(expected = IllegalArgumentException.class)
    public void testNotNull_nullValuePossible()
    {
        NotEmptyBuilder builder = factory.createBuilderForClass(NotEmptyBuilder.class);
        NotEmptyBuiltObject builtObject = builder.stringValue(null).build();
        assertThat(builtObject.getStringValue(), is((String) null));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNotNull_emptyValue()
    {
        factory.createBuilderForClass(NotEmptyBuilder.class).stringValue("");
    }

    private interface NotEmptyBuilder
    {
        NotEmptyBuilder stringValue(@Not({ NULL, EMPTY }) String value);

        NotEmptyBuiltObject build();
    }

    private interface NotEmptyBuiltObject
    {
        String getStringValue();
    }
}
