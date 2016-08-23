package com.singleton.dynamic.builder.integration.validation;

import static com.singleton.dynamic.builder.validation.NotParameterValidator.EMPTY;
import static com.singleton.dynamic.builder.validation.NotParameterValidator.NULL;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Collections;

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
    public void testNotEmptyNotNull_nullStringNotAllowed()
    {
        factory.createBuilderForClass(NotNullOrEmptStringBuilder.class).stringValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEmptyNotNull_emptyStringNotAllowed()
    {
        factory.createBuilderForClass(NotNullOrEmptStringBuilder.class).stringValue("");
    }

    @Test
    public void testNotEmptyNotNull_stringWithValueAllowed()
    {
        NotNullOrEmptStringBuilder builder = factory.createBuilderForClass(NotNullOrEmptStringBuilder.class);

        assertThat(builder.stringValue("value").build().getStringValue(), is("value"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEmptyNotNull_nullCollectionNotAllowed()
    {
        factory.createBuilderForClass(NotNullOrEmptyCollectionBuilder.class).collectionValue(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotEmptyNotNull_emptyCollectionNotAllowed()
    {
        factory.createBuilderForClass(NotNullOrEmptyCollectionBuilder.class).collectionValue(Collections.<Long> emptyList());
    }

    @Test
    public void testNotEmptyNotNull_collectionWithValuesAllowed()
    {
        Collection<Long> expectedValue = Collections.singleton(1L);
        NotNullOrEmptyCollectionBuilder builder = factory.createBuilderForClass(NotNullOrEmptyCollectionBuilder.class);

        Collection<Long> actualValue = builder.collectionValue(Collections.singleton(1L)).build().getCollectionValue();

        assertThat(actualValue, is(expectedValue));
    }

    private interface NotNullOrEmptyCollectionBuilder
    {
        NotNullOrEmptyCollectionBuilder collectionValue(@Not({ NULL, EMPTY }) Collection<Long> collectionValue);

        NotNullOrEmptyCollectionBuiltObject build();
    }

    private interface NotNullOrEmptyCollectionBuiltObject
    {
        Collection<Long> getCollectionValue();
    }

    private interface NotNullOrEmptStringBuilder
    {
        NotNullOrEmptStringBuilder stringValue(@Not({ NULL, EMPTY }) String value);

        NotNullOrEmptyStringBuiltObject build();
    }

    private interface NotNullOrEmptyStringBuiltObject
    {
        String getStringValue();
    }
}
