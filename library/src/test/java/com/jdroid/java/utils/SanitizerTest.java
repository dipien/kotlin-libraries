package com.jdroid.java.utils;

import com.jdroid.java.collections.Lists;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class SanitizerTest {

	@DataProvider
	public Iterator<Object[]> plainStringDataProvider() {
		List<Object[]> cases = Lists.newArrayList();

		// removingAccentsInLowercase
		cases.add(new Object[] { "bcdfghjklmnñpqrstvwxyz", "bcdfghjklmnnpqrstvwxyz" });
		cases.add(new Object[] { "áéíóú", "aeiou" });
		cases.add(new Object[] { "äëïöü", "aeiou" });
		cases.add(new Object[] { "àèìòù", "aeiou" });
		cases.add(new Object[] { "âêîôû", "aeiou" });
		cases.add(new Object[] { "ãõ", "ao"});
		cases.add(new Object[] { "å", "a"});

		// removingAccentsInUppercase
		cases.add(new Object[] { "BCDFGHJKLMNÑPQRSTVWXYZ", "BCDFGHJKLMNNPQRSTVWXYZ" });
		cases.add(new Object[] { "ÁÉÍÓÚ", "AEIOU" });
		cases.add(new Object[] { "ÄËÏÖÜ", "AEIOU" });
		cases.add(new Object[] { "ÀÈÌÒÙ", "AEIOU" });
		cases.add(new Object[] { "ÂÊÎÔÛ", "AEIOU" });
		cases.add(new Object[] { "ÃÕ", "AO" });
		cases.add(new Object[] { "Å", "A" });

		return cases.iterator();
	}

	@Test(dataProvider = "plainStringDataProvider")
	public void plainString(String value, String expected) {
		assertEquals(Sanitizer.plainString(value), expected);
	}
	
	@Test
	public void removingNumbers() {
		assertEquals(Sanitizer.plainStringWithoutNumbers("1213123BCDF7G6H5JK4LM3N1ÑPQ2RSTVWXYZ888"),
			"BCDFGHJKLMNNPQRSTVWXYZ");
	}
	
	@Test
	public void removingExtraSpaces() {
		assertEquals(Sanitizer.plainStringWithoutExtraSpaces(" B     C DF GH JKL MNÑP QRST VWXY Z "),
			"B C DF GH JKL MNNP QRST VWXY Z");
	}
	
	@Test
	public void removeNumbers() {
		assertEquals(Sanitizer.removeNumbers("5BCDF5G6H5JK4LM3N1ÑPQ2RSTVWXYZ888"), "BCDFGHJKLMNÑPQRSTVWXYZ");
	}
	
	@Test
	public void removingNonNumbers() {
		assertEquals(Sanitizer.justNumbers("5BCDF5G6H5JK4LM3N1ÑPQ2RSTVWXYZ888"), "55654312888");
	}
}
