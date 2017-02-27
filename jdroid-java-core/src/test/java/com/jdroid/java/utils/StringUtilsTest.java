package com.jdroid.java.utils;

import com.jdroid.java.Assert;
import com.jdroid.java.collections.Lists;
import com.jdroid.java.collections.Sets;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

public class StringUtilsTest {

	@Test
	public void isStringBlank() {
		assertTrue(StringUtils.isBlank(" "));
		assertTrue(StringUtils.isBlank(null));
		assertTrue(StringUtils.isBlank(""));
	}

	@Test
	public void isStringEmpty() {
		assertTrue(StringUtils.isEmpty(""));
		assertFalse(StringUtils.isEmpty(" "));
		assertTrue(StringUtils.isEmpty(null));
	}

	@Test
	public void isNotBlank() {
		assertFalse(StringUtils.isNotBlank(""));
		assertTrue(StringUtils.isNotBlank("asdadsa"));
		assertFalse(StringUtils.isNotBlank(null));
		assertFalse(StringUtils.isNotBlank(" "));
	}

	@Test
	public void areTwoStringsEquals() {
		assertTrue(StringUtils.equal("hola", "hola"));
		assertFalse(StringUtils.equal("hola", "holaa"));
	}

	@Test
	public void capitalizing() {
		assertEquals(StringUtils.capitalize("hola"), "Hola");
		assertFalse("hola".equals(StringUtils.capitalize("hola")));
	}

	@Test
	public void defaultStringShouldNotBeNull() {
		assertEquals(StringUtils.defaultString("hola"), "hola");
		assertEquals(StringUtils.defaultString(null), "");
		assertEquals(StringUtils.defaultString(""), "");
		assertEquals(StringUtils.defaultString(null), "");
	}

	@Test
	public void getFirstToken() {
		assertEquals(StringUtils.getFirstToken("hola,como,estas"), "hola");
		assertEquals(StringUtils.getFirstToken("hola;como;estas", ";"), "hola");
	}

	@Test
	public void getNotEmptyString() {
		assertEquals(StringUtils.getNotEmptyString(""), null);
		assertEquals(StringUtils.getNotEmptyString("holi"), "holi");
	}

	@Test
	public void hasOnlyCharacters() {
		assertTrue(StringUtils.hasOnlyCharacters("asdqewzcxzxcqeasdr"));
		assertFalse(StringUtils.hasOnlyCharacters("asdqewzcxzxcq2easdr"));
	}

	@Test
	public void removeNonAlphanumericCharacters() {
		assertEquals(StringUtils.toAlphanumeric("12sa'sd"), "12sasd");
		assertEquals(StringUtils.toAlphanumeric("sasd12"), "sasd12");
	}

	/**
	 * @return The different scenarios for test {@link StringUtils#toAlphanumeric(String)}
	 */
	@DataProvider
	public Iterator<Object[]> toAlphanumericProvider() {
		List<Object[]> cases = Lists.newArrayList();
		cases.add(new Object[]{"text", "text"});
		cases.add(new Object[]{"text.", "text"});
		cases.add(new Object[]{"text.\\", "text"});
		cases.add(new Object[]{"?text.\\", "text"});
		cases.add(new Object[]{" ?text.\\", " text"});
		cases.add(new Object[]{"text.\\text2", "texttext2"});
		cases.add(new Object[]{"text.\\text2??", "texttext2"});
		cases.add(new Object[]{"text.\\text2??text3", "texttext2text3"});
		cases.add(new Object[]{"text .\\text2 ??text3", "text text2 text3"});
		cases.add(new Object[]{"text text2 text3", "text text2 text3"});
		cases.add(new Object[]{"text  text2", "text  text2"});
		return cases.iterator();
	}

	/**
	 * @param text         The text to transform
	 * @param expectedText The expected transformed text
	 */
	@Test(dataProvider = "toAlphanumericProvider")
	public void toAlphanumeric(String text, String expectedText) {
		assertEquals(StringUtils.toAlphanumeric(text), expectedText);
	}

	/**
	 * @return The different scenarios for test {@link StringUtils#truncate(String, Integer)}
	 */
	@DataProvider
	public Iterator<Object[]> truncateProvider() {
		List<Object[]> cases = Lists.newArrayList();
		cases.add(new Object[]{null, 5, null, null});
		cases.add(new Object[]{"", 5, "", ""});
		cases.add(new Object[]{"text1", 1, "t", ""});
		cases.add(new Object[]{"text1", 2, "te", ""});
		cases.add(new Object[]{"text1", 3, "tex", ""});
		cases.add(new Object[]{"text1", 4, "t...", ""});
		cases.add(new Object[]{"text1", 5, "text1", "text1"});
		cases.add(new Object[]{"text1", 6, "text1", "text1"});
		cases.add(new Object[]{"text1", 10, "text1", "text1"});
		cases.add(new Object[]{"text1 text2", 4, "t...", ""});
		cases.add(new Object[]{"text1 text2", 5, "te...", "text1"});
		cases.add(new Object[]{"text1 text2", 6, "tex...", "text1"});
		cases.add(new Object[]{"text1 text2", 7, "text...", "text1"});
		cases.add(new Object[]{"text1 text2", 11, "text1 text2", "text1 text2"});
		cases.add(new Object[]{"text1 text2 ", 11, "text1 te...", "text1 text2"});
		cases.add(new Object[]{"text1  text2 ", 11, "text1  t...", "text1 "});
		return cases.iterator();
	}

	/**
	 * @param text                          The text to truncate
	 * @param maxCharacters                 The maximum amount of characters allowed
	 * @param expectedWordsTruncatedText    The expected truncated text with words truncated
	 * @param expectedWordsNotTruncatedText The expected truncated text without words truncated
	 */
	@Test(dataProvider = "truncateProvider")
	public void truncate(String text, Integer maxCharacters, String expectedWordsTruncatedText,
						 String expectedWordsNotTruncatedText) {
		assertEquals(StringUtils.truncate(text, maxCharacters), expectedWordsTruncatedText);
		assertEquals(StringUtils.truncate(text, maxCharacters, true), expectedWordsTruncatedText);
		assertEquals(StringUtils.truncate(text, maxCharacters, false), expectedWordsNotTruncatedText);
	}

	/**
	 * @return The different scenarios
	 */
	@DataProvider
	public Iterator<Object[]> extractPlaceholdersProvider() {
		List<Object[]> cases = Lists.newArrayList();
		cases.add(new Object[]{"", Sets.newHashSet()});
		cases.add(new Object[]{"a", Sets.newHashSet()});
		cases.add(new Object[]{"${a}", Sets.newHashSet("a")});
		cases.add(new Object[]{"${a}${a}", Sets.newHashSet("a")});
		cases.add(new Object[]{"${a}${b}", Sets.newHashSet("a", "b")});
		return cases.iterator();
	}

	/**
	 * Verifies the {@link StringUtils#extractPlaceHolders(String)} method
	 *
	 * @param string           The string
	 * @param placeholderNames The placeholder names
	 */
	@Test(dataProvider = "extractPlaceholdersProvider")
	public void extractPlaceholders(String string, Set<String> placeholderNames) {
		Set<String> result = StringUtils.extractPlaceHolders(string);
		Assert.assertEqualsNoOrder(result, placeholderNames);
	}

	@Test
	public void wordWrapToTwoLinesTests() {
		// Word wrap with two words of the same size
		assertEquals(StringUtils.wordWrapToTwoLines("Aerolineas Argentinas", 10),
				"Aerolineas\nArgentinas");
		// Word wrap with the first word longer than the second
		assertEquals(StringUtils.wordWrapToTwoLines("Southern Winds", 10), "Southern\nWinds");
		// Word wrap with the first word shorter than the second
		assertEquals(StringUtils.wordWrapToTwoLines("Virgin Airlines", 10), "Virgin\nAirlines");
		// Without Word wrap, text length less than the minimum
		assertEquals(StringUtils.wordWrapToTwoLines("Swiss Air", 10), "Swiss Air");
		// Without Word wrap, text length less than minimun and one word
		assertEquals(StringUtils.wordWrapToTwoLines("Tam", 10), "Tam");
		// Without Word wrap, text length greater than minimun and one word
		assertEquals(StringUtils.wordWrapToTwoLines("LargeNameAirline", 10), "LargeNameAirline");
		// Word wrap with more than two words
		assertEquals(StringUtils.wordWrapToTwoLines("Large Name Airline", 10), "Large Name\nAirline");
	}

	@Test
	public void hasOnlyCharacterTest() {
		assertTrue(StringUtils.hasOnlyCharacters("Jhon"));
		assertTrue(StringUtils.hasOnlyCharacters("Jhon Wayne"));
		assertTrue(StringUtils.hasOnlyCharacters(" Jhon  Nicholas   Wayne     "));
		assertFalse(StringUtils.hasOnlyCharacters("Jhon2"));
		assertFalse(StringUtils.hasOnlyCharacters("Jhon 5"));
		assertFalse(StringUtils.hasOnlyCharacters("5Jhon "));
		assertFalse(StringUtils.hasOnlyCharacters("Jhon Wayne the 3rd"));
		assertFalse(StringUtils.hasOnlyCharacters("Jhon Wayne the 3rd."));
		assertFalse(StringUtils.hasOnlyCharacters("Jhon Wayne the 3rd _ "));
	}
}
