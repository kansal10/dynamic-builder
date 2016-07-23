package com.singleton.dynamic.builder.integration.validation;

import static com.singleton.dynamic.builder.validation.NotParameterValidator.EMPTY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Collections;

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

    @Test
    public void testNotNull_nullStringValueAllowed()
    {
        NotEmptyBuilder builder = factory.createBuilderForClass(NotEmptyBuilder.class);

        NotEmptyBuiltObject builtObject = builder.stringValue(null).build();
        assertThat(builtObject.getStringValue(), is((String) null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotAnnotation_emptyStringValueNotAllowed()
    {
        factory.createBuilderForClass(NotEmptyBuilder.class).stringValue("");
    }

    @Test
    public void testNotAnnotation_nullCollectionValueAllowed()
    {
        NotEmptyCollectionBuilder builder = factory.createBuilderForClass(NotEmptyCollectionBuilder.class).collectionValue(null);

        assertThat(builder.build().getCollectionValue(), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotAnnoitation_emptyCollectionValueNotAllowed()
    {
        factory.createBuilderForClass(NotEmptyCollectionBuilder.class).collectionValue(Collections.<Long> emptyList());
    }

    private interface NotEmptyCollectionBuilder
    {
        NotEmptyCollectionBuilder collectionValue(@Not({ EMPTY }) Collection<Long> value);

        NotEmptyCollectionObject build();
    }
    
    private interface NotEmptyCollectionObject
    {
        Collection<Long> getCollectionValue();
    }

    private interface NotEmptyBuilder
    {
        NotEmptyBuilder stringValue(@Not({ EMPTY }) String value);

        NotEmptyBuiltObject build();
    }

    private interface NotEmptyBuiltObject
    {
        String getStringValue();
    }
}