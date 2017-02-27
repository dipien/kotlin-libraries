package com.jdroid.java.marshaller;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.collections.Maps;
import com.jdroid.java.json.JsonMap;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MarshallerProviderTest {
	
	private MarshallerProvider marshallerProvider;
	
	public MarshallerProviderTest() {
		marshallerProvider = MarshallerProvider.get();
		marshallerProvider.addMarshaller(DummyClass.class, new DummyClassMarshaller());
	}
	
	@DataProvider
	public Iterator<Object[]> marshallDataProvider() {
		List<Object[]> cases = Lists.newArrayList();
		cases.add(new Object[] { 1, "1" });
		cases.add(new Object[] { "1", "1" });
		cases.add(new Object[] { Lists.newArrayList(1, 2, 3), "[1,2,3]" });
		cases.add(new Object[] { Lists.newArrayList("1", "2", "3"), "[\"1\",\"2\",\"3\"]" });
		
		DummyClass dummyClass = new DummyClass();
		dummyClass.stringProperty = "1";
		String dummyJson = "{\"stringProperty\":\"1\"}";
		cases.add(new Object[] { dummyClass, dummyJson });
		cases.add(new Object[] { Lists.newArrayList(dummyClass, dummyClass), "[" + dummyJson + "," + dummyJson + "]" });

		dummyClass = new DummyClass();
		dummyClass.longProperty = 2L;
		dummyJson = "{\"longProperty\":2}";
		cases.add(new Object[] { dummyClass, dummyJson });
		cases.add(new Object[] { Lists.newArrayList(dummyClass, dummyClass), "[" + dummyJson + "," + dummyJson + "]" });

		dummyClass = new DummyClass();
		dummyClass.stringListProperty = Lists.newArrayList("3", "4");
		dummyJson = "{\"stringListProperty\":[\"3\",\"4\"]}";
		cases.add(new Object[] { Lists.newArrayList(dummyClass, dummyClass), "[" + dummyJson + "," + dummyJson + "]" });

		dummyClass = new DummyClass();
		dummyClass.longListProperty = Lists.newArrayList(5L, 6L);
		dummyJson = "{\"longListProperty\":[5,6]}";
		cases.add(new Object[] { dummyClass, dummyJson });
		cases.add(new Object[] { Lists.newArrayList(dummyClass, dummyClass), "[" + dummyJson + "," + dummyJson + "]" });

		dummyClass = new DummyClass();
		dummyClass.stringMapProperty = Maps.newLinkedHashMap();
		dummyJson = "{}";
		cases.add(new Object[] { dummyClass, dummyJson });
		cases.add(new Object[] { Lists.newArrayList(dummyClass, dummyClass), "[" + dummyJson + "," + dummyJson + "]" });

		dummyClass = new DummyClass();
		dummyClass.stringMapProperty = Maps.newLinkedHashMap();
		dummyClass.stringMapProperty.put("a", "b");
		dummyJson = "{\"stringMapProperty\":{\"a\":\"b\"}}";
		cases.add(new Object[] { dummyClass, dummyJson });
		cases.add(new Object[] { Lists.newArrayList(dummyClass, dummyClass), "[" + dummyJson + "," + dummyJson + "]" });

		dummyClass = new DummyClass();
		dummyClass.longMapProperty = Maps.newLinkedHashMap();
		dummyClass.longMapProperty.put("a", 1L);
		dummyJson = "{\"longMapProperty\":{\"a\":1}}";
		cases.add(new Object[] { dummyClass, dummyJson });
		cases.add(new Object[] { Lists.newArrayList(dummyClass, dummyClass), "[" + dummyJson + "," + dummyJson + "]" });

		dummyClass = new DummyClass();
		dummyClass.dummyClassMapProperty = Maps.newLinkedHashMap();

		DummyClass innerDummyClass = new DummyClass();
		innerDummyClass.stringProperty = "2";
		innerDummyClass.longProperty = 1L;

		dummyClass.dummyClassMapProperty.put("a", innerDummyClass);
		dummyJson = "{\"dummyClassMapProperty\":{\"a\":{\"longProperty\":1,\"stringProperty\":\"2\"}}}";
		cases.add(new Object[] { dummyClass, dummyJson });
		cases.add(new Object[] { Lists.newArrayList(dummyClass, dummyClass), "[" + dummyJson + "," + dummyJson + "]" });

		cases.add(new Object[] { new DummyClass(), "{}" });
		
		return cases.iterator();
	}
	
	@Test(dataProvider = "marshallDataProvider")
	public void marshall(Object data, String expectedJson) {
		String result = marshallerProvider.marshall(data, null, null).toString();
		Assert.assertEquals(result.replace(" ", ""), expectedJson);
	}
	
	private class DummyClass {
		
		protected String stringProperty;
		protected Long longProperty;
		protected List<String> stringListProperty;
		protected List<Long> longListProperty;
		protected Map<String, String> stringMapProperty;
		protected Map<String, Long> longMapProperty;
		protected Map<String, DummyClass> dummyClassMapProperty;
	}
	
	public class DummyClassMarshaller implements Marshaller<DummyClass, JsonMap> {
		
		@Override
		public JsonMap marshall(DummyClass dummyClass, MarshallerMode mode, Map<String, String> extras) {
			JsonMap map = new JsonMap(mode, extras);
			map.put("stringProperty", dummyClass.stringProperty);
			map.put("longProperty", dummyClass.longProperty);
			map.put("stringListProperty", dummyClass.stringListProperty);
			map.put("longListProperty", dummyClass.longListProperty);
			map.put("stringMapProperty", dummyClass.stringMapProperty);
			map.put("longMapProperty", dummyClass.longMapProperty);
			map.put("dummyClassMapProperty", dummyClass.dummyClassMapProperty);
			return map;
		}
	}
}
