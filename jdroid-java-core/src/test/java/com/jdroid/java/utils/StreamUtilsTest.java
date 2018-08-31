package com.jdroid.java.utils;

import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

public class StreamUtilsTest {

	@Test
	public void isEquals() {
		InputStream inputStream1 = new ByteArrayInputStream("123".getBytes());
		InputStream inputStream2 = new ByteArrayInputStream("123".getBytes());
		InputStream inputStream3 = new ByteArrayInputStream("124".getBytes());

		assertTrue(StreamUtils.isEquals(inputStream1, inputStream2));
		assertFalse(StreamUtils.isEquals(inputStream1, inputStream3));
	}

}
