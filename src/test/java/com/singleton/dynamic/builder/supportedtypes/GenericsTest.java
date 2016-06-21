package com.singleton.dynamic.builder.supportedtypes;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.singleton.dynamic.builder.DynamicBuilderFactory;

/**
 * Test that ensures that the dynamic builder supports generics.
 *
 * @author Dustin Singleton
 */
public class GenericsTest {
	private final DynamicBuilderFactory factory = new DynamicBuilderFactory();
	
	@Test
	public void testList() {
		List<String> stringList = new ArrayList<String>();
		stringList.add("a");
		stringList.add("b");
		
		ListBuilder builder = factory.createBuilderForClass(ListBuilder.class);
		builder.stringList(stringList);
		
		assertThat(builder.build().getStringList(), is(stringList));
	}
	
	private interface ListBuilder {
		ListBuilder stringList(List<String> stringList);
		
		ListBuiltObject build();
	}
	
	private interface ListBuiltObject {
		List<String> getStringList();
	}
}
