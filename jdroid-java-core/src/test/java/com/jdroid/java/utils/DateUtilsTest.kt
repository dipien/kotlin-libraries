package com.jdroid.java.utils

import com.jdroid.java.date.DateConfiguration
import com.jdroid.java.date.DateTimeFormat
import com.jdroid.java.date.DateUtils
import org.testng.Assert
import org.testng.annotations.AfterMethod
import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import java.util.Calendar
import java.util.Date

/**
 * Test class for the [DateUtils] class.
 */
class DateUtilsTest {

    @AfterMethod
    fun onAfterMethod() {
        DateConfiguration.setFakeNow(null)
    }

    /**
     * @return The different cases
     */
    @DataProvider
    fun formatDataProvider(): Iterator<Array<Any>> {
        val cases = mutableListOf<Array<Any>>()
        cases.add(arrayOf(DateUtils.getDate(2010, Calendar.AUGUST, 10), DateTimeFormat.MMDDYYYY, "08/10/2010"))
        cases.add(arrayOf(DateUtils.getDate(2010, Calendar.AUGUST, 10), DateTimeFormat.MMDDYYYY_SLASH, "08-10-2010"))
        cases.add(arrayOf(DateUtils.getTime(5, 30, true), DateTimeFormat.HHMMAA, "05:30 AM"))
        return cases.iterator()
    }

    /**
     * @param date The [Date] to format
     * @param pattern The pattern to format the [Date]
     * @param expected The expected string
     */
    @Test(dataProvider = "formatDataProvider")
    fun format(date: Date, pattern: String, expected: String) {
        val result = DateUtils.format(date, pattern)
        Assert.assertEquals(result, expected)
    }

    /**
     * Test method for the [DateUtils.isBetween] method.
     *
     * @return The different scenarios
     */
    @DataProvider
    fun getForIsBetween(): Iterator<Array<Any>> {
        val calendar = Calendar.getInstance()
        calendar.set(2009, Calendar.JANUARY, 20)
        val date = calendar.time

        val cases = mutableListOf<Array<Any>>()
        calendar.set(2009, Calendar.JANUARY, 20)
        var startDate = calendar.time
        calendar.set(2009, Calendar.JANUARY, 20)
        var endDate = calendar.time
        cases.add(arrayOf(date, startDate, endDate, true))

        calendar.set(2009, Calendar.JANUARY, 19)
        startDate = calendar.time
        calendar.set(2009, Calendar.JANUARY, 21)
        endDate = calendar.time
        cases.add(arrayOf(date, startDate, endDate, true))

        calendar.set(2009, Calendar.JANUARY, 19)
        startDate = calendar.time
        calendar.set(2009, Calendar.JANUARY, 19)
        endDate = calendar.time
        cases.add(arrayOf(date, startDate, endDate, false))

        return cases.iterator()
    }

    /**
     * Test method for the [DateUtils.isBetween] method.
     *
     * @param date The date
     * @param startDate The start date
     * @param endDate The end date
     * @param expectedResult The expected result
     */
    @Test(dataProvider = "getForIsBetween")
    fun isBetween(date: Date, startDate: Date, endDate: Date, expectedResult: Boolean) {
        Assert.assertEquals(DateUtils.isBetween(date, startDate, endDate), expectedResult)
    }

    /**
     * Tests the [DateUtils.getDate] method.
     */
    @Test
    fun getDate() {
        val calendar = Calendar.getInstance()
        val year = 2009
        val month = Calendar.JANUARY
        val date = 1
        calendar.set(year, month, date)
        DateUtils.truncateTime(calendar)
        val createdDate = DateUtils.getDate(year, month, date)
        Assert.assertEquals(createdDate, calendar.time)
    }

    /**
     * So given the start date is 10-Jun and stop date is 20-Jun there are several scenarios to check:
     *
     *
     * <pre>
     * Input:  --------|-----|---------
     * Case1:  --------|-----|---------  Yes
     * Case2:  ----------|-|-----------  Yes
     * Case3:  --------|-|-------------  Yes
     * Case4:  ------------|-|---------  Yes
     * Case5:  -----|--|---------------  Yes
     * Case6:  -----|----|-------------  Yes
     * Case7:  --------------|---|-----  Yes
     * Case8:  ----------- |-----|-----  Yes
     * Case9:  -----|------------|-----  Yes
     * Case10: --|--|------------------  No
     * Case11: ------------------|--|--  No
    </pre> *
     *
     *
     * In other words, ONLY if one range touches the other, it's an overlap.
     *
     * @return [Iterator] Contains the test cases.
     */
    @DataProvider
    fun periodsOverlapDataProvider(): Iterator<Array<Any>> {
        val cases = mutableListOf<Array<Any>>()
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.JANUARY
        cases.add(arrayOf(DateUtils.getDate(year, month, 10), DateUtils.getDate(year, month, 20), true))
        cases.add(arrayOf(DateUtils.getDate(year, month, 12), DateUtils.getDate(year, month, 18), true))
        cases.add(arrayOf(DateUtils.getDate(year, month, 10), DateUtils.getDate(year, month, 12), true))
        cases.add(arrayOf(DateUtils.getDate(year, month, 18), DateUtils.getDate(year, month, 20), true))
        cases.add(arrayOf(DateUtils.getDate(year, month, 8), DateUtils.getDate(year, month, 10), true))
        cases.add(arrayOf(DateUtils.getDate(year, month, 8), DateUtils.getDate(year, month, 12), true))
        cases.add(arrayOf(DateUtils.getDate(year, month, 20), DateUtils.getDate(year, month, 22), true))
        cases.add(arrayOf(DateUtils.getDate(year, month, 18), DateUtils.getDate(year, month, 22), true))
        cases.add(arrayOf(DateUtils.getDate(year, month, 8), DateUtils.getDate(year, month, 22), true))
        cases.add(arrayOf(DateUtils.getDate(year, month, 6), DateUtils.getDate(year, month, 8), false))
        cases.add(arrayOf(DateUtils.getDate(year, month, 22), DateUtils.getDate(year, month, 24), false))
        return cases.iterator()
    }

    /**
     * @param start2 The second rage start date
     * @param end2 The second rage end date
     * @param expected the expected overlap result between the current rage and the scenario
     */
    @Test(dataProvider = "periodsOverlapDataProvider")
    fun periodsOverlapDateTest(start2: Date, end2: Date, expected: Boolean) {

        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.JANUARY

        val start = DateUtils.getDate(year, month, 10)
        val end = DateUtils.getDate(year, month, 20)

        Assert.assertEquals(DateUtils.periodsOverlap(start, end, start2, end2), expected)
    }

    /**
     * @return The different scenarios of periods
     */
    @DataProvider
    fun getForIsAfterEquals(): Iterator<Array<Any>> {
        val cases = mutableListOf<Array<Any>>()
        cases.add(arrayOf(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 11, true))
        cases.add(arrayOf(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 12, false))
        cases.add(arrayOf(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 10, true))
        return cases.iterator()
    }

    /**
     * @param year The year
     * @param month The month
     * @param date The date
     * @param yearToCompare The year to compare with
     * @param monthToCompare The month to compare with
     * @param dateToCompare The date to compare with
     * @param expectedResult The expectedResult
     */
    @Test(dataProvider = "getForIsAfterEquals")
    fun isAfterEquals(
        year: Int,
        month: Int,
        date: Int,
        yearToCompare: Int,
        monthToCompare: Int,
        dateToCompare: Int,
        expectedResult: Boolean
    ) {
        Assert.assertEquals(
            DateUtils.isAfterEquals(
                DateUtils.getDate(year, month, date),
                DateUtils.getDate(yearToCompare, monthToCompare, dateToCompare)
            ), expectedResult
        )
    }

    /**
     * @return The different scenarios of periods
     */
    @DataProvider
    fun getForIsAfter(): Iterator<Array<Any>> {
        val cases = mutableListOf<Array<Any>>()
        cases.add(arrayOf(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 11, false))
        cases.add(arrayOf(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 12, false))
        cases.add(arrayOf(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 10, true))
        return cases.iterator()
    }

    /**
     * @param year The year
     * @param month The month
     * @param date The date
     * @param yearToCompare The year to compare with
     * @param monthToCompare The month to compare with
     * @param dateToCompare The date to compare with
     * @param expectedResult The expectedResult
     */
    @Test(dataProvider = "getForIsAfter")
    fun isAfter(
        year: Int,
        month: Int,
        date: Int,
        yearToCompare: Int,
        monthToCompare: Int,
        dateToCompare: Int,
        expectedResult: Boolean
    ) {
        Assert.assertEquals(
            DateUtils.isAfter(
                DateUtils.getDate(year, month, date),
                DateUtils.getDate(yearToCompare, monthToCompare, dateToCompare)
            ), expectedResult
        )
    }

    /**
     * @return The different scenarios of periods
     */
    @DataProvider
    fun getForIsBeforeEquals(): Iterator<Array<Any>> {
        val cases = mutableListOf<Array<Any>>()
        cases.add(arrayOf(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 11, true))
        cases.add(arrayOf(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 12, true))
        cases.add(arrayOf(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 10, false))
        return cases.iterator()
    }

    /**
     * @param year The year
     * @param month The month
     * @param date The date
     * @param yearToCompare The year to compare with
     * @param monthToCompare The month to compare with
     * @param dateToCompare The date to compare with
     * @param expectedResult The expectedResult
     */
    @Test(dataProvider = "getForIsBeforeEquals")
    fun isBeforeEquals(
        year: Int,
        month: Int,
        date: Int,
        yearToCompare: Int,
        monthToCompare: Int,
        dateToCompare: Int,
        expectedResult: Boolean
    ) {
        Assert.assertEquals(
            DateUtils.isBeforeEquals(
                DateUtils.getDate(year, month, date),
                DateUtils.getDate(yearToCompare, monthToCompare, dateToCompare)
            ), expectedResult
        )
    }

    /**
     * @return The different scenarios of periods
     */
    @DataProvider
    fun getForIsBefore(): Iterator<Array<Any>> {
        val cases = mutableListOf<Array<Any>>()
        cases.add(arrayOf(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 11, false))
        cases.add(arrayOf(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 12, true))
        cases.add(arrayOf(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 10, false))
        return cases.iterator()
    }

    /**
     * @param year The year
     * @param month The month
     * @param date The date
     * @param yearToCompare The year to compare with
     * @param monthToCompare The month to compare with
     * @param dateToCompare The date to compare with
     * @param expectedResult The expectedResult
     */
    @Test(dataProvider = "getForIsBefore")
    fun isBefore(
        year: Int,
        month: Int,
        date: Int,
        yearToCompare: Int,
        monthToCompare: Int,
        dateToCompare: Int,
        expectedResult: Boolean
    ) {
        Assert.assertEquals(
            DateUtils.isBefore(
                DateUtils.getDate(year, month, date),
                DateUtils.getDate(yearToCompare, monthToCompare, dateToCompare)
            ), expectedResult
        )
    }

    /**
     * @param date the date to be tested
     * @param expectedResult The expected result
     */
    @Test(dataProvider = "getLastDayOfTheMonthData", dependsOnMethods = ["getDate"])
    fun lastDayOfTheMonth(date: Date, expectedResult: Date) {
        val lastDay = DateUtils.getLastDayOfMonth(date)
        Assert.assertEquals(lastDay, expectedResult)
    }

    /**
     * @return the data to tests the lastDayOfTheMonth method
     */
    @DataProvider
    protected fun getLastDayOfTheMonthData(): Iterator<Array<Any>> {
        val cases = mutableListOf<Array<Any>>()

        // 31-day month
        val date1 = DateUtils.getDate(2010, 0, 10)
        val expectedDate1 = DateUtils.getDate(2010, 0, 31)
        cases.add(arrayOf(date1, expectedDate1))

        // 31-day month but in the 31th day
        val date2 = DateUtils.getDate(2010, 0, 31)
        val expectedDate2 = DateUtils.getDate(2010, 0, 31)
        cases.add(arrayOf(date2, expectedDate2))

        // 30-day month
        val date3 = DateUtils.getDate(2010, 3, 1)
        val expectedDate3 = DateUtils.getDate(2010, 3, 30)
        cases.add(arrayOf(date3, expectedDate3))

        // February special case in a leap year
        val date4 = DateUtils.getDate(2008, 1, 1)
        val expectedDate4 = DateUtils.getDate(2008, 1, 29)
        cases.add(arrayOf(date4, expectedDate4))

        // February special case in a normal year
        val date5 = DateUtils.getDate(2009, 1, 1)
        val expectedDate5 = DateUtils.getDate(2009, 1, 28)
        cases.add(arrayOf(date5, expectedDate5))

        return cases.iterator()
    }

    @Test(dataProvider = "getLastWeekDayOfTheMonthData")
    fun lastWeekDayOfTheMonth(date: Date, expectedResult: Date) {
        val lastDay = DateUtils.getLastWeekDayOfMonth(date)
        Assert.assertEquals(lastDay, expectedResult)
    }

    @DataProvider
    protected fun getLastWeekDayOfTheMonthData(): Iterator<Array<Any>> {
        val cases = mutableListOf<Array<Any>>()

        // 31-day month
        val date1 = DateUtils.getDate(2017, 11, 1)
        val expectedDate1 = DateUtils.getDate(2017, 11, 29)
        cases.add(arrayOf(date1, expectedDate1))

        // 31-day month but in the 31th day
        val date2 = DateUtils.getDate(2017, 11, 31)
        val expectedDate2 = DateUtils.getDate(2017, 11, 29)
        cases.add(arrayOf(date2, expectedDate2))

        // 30-day month
        val date3 = DateUtils.getDate(2017, 10, 1)
        val expectedDate3 = DateUtils.getDate(2017, 10, 30)
        cases.add(arrayOf(date3, expectedDate3))

        // February special case in a leap year
        val date4 = DateUtils.getDate(2008, 1, 1)
        val expectedDate4 = DateUtils.getDate(2008, 1, 29)
        cases.add(arrayOf(date4, expectedDate4))

        // February special case in a normal year
        val date5 = DateUtils.getDate(2009, 1, 1)
        val expectedDate5 = DateUtils.getDate(2009, 1, 27)
        cases.add(arrayOf(date5, expectedDate5))

        return cases.iterator()
    }

    /**
     * @param date the date to be tested
     * @param expectedResult The expected result
     */
    @Test(dataProvider = "getLastDayOfTheYearData", dependsOnMethods = ["getDate"])
    fun lastDayOfTheYear(date: Date, expectedResult: Date) {
        val lastDay = DateUtils.getLastDayOfYear(date)
        Assert.assertEquals(lastDay, expectedResult)
    }

    @DataProvider
    protected fun getLastDayOfTheYearData(): Iterator<Array<Any>> {
        val cases = mutableListOf<Array<Any>>()

        // 31-day month
        val date1 = DateUtils.getDate(2010, 0, 10)
        val expectedDate1 = DateUtils.getDate(2010, 11, 31)
        cases.add(arrayOf(date1, expectedDate1))

        // 31-day month but in the 31th day
        val date2 = DateUtils.getDate(2010, 0, 31)
        val expectedDate2 = DateUtils.getDate(2010, 11, 31)
        cases.add(arrayOf(date2, expectedDate2))

        // 30-day month
        val date3 = DateUtils.getDate(2010, 3, 1)
        val expectedDate3 = DateUtils.getDate(2010, 11, 31)
        cases.add(arrayOf(date3, expectedDate3))

        // February special case in a leap year
        val date4 = DateUtils.getDate(2008, 1, 1)
        val expectedDate4 = DateUtils.getDate(2008, 11, 31)
        cases.add(arrayOf(date4, expectedDate4))

        // February special case in a normal year
        val date5 = DateUtils.getDate(2009, 1, 1)
        val expectedDate5 = DateUtils.getDate(2009, 11, 31)
        cases.add(arrayOf(date5, expectedDate5))

        return cases.iterator()
    }

    @Test(dataProvider = "getLastWeekDayOfTheYearData")
    fun lastWeekDayOfTheYear(date: Date, expectedResult: Date) {
        val lastDay = DateUtils.getLastWeekDayOfYear(date)
        Assert.assertEquals(lastDay, expectedResult)
    }

    @DataProvider
    protected fun getLastWeekDayOfTheYearData(): Iterator<Array<Any>> {
        val cases = mutableListOf<Array<Any>>()

        // 31-day month
        val date1 = DateUtils.getDate(2017, 11, 1)
        val expectedDate1 = DateUtils.getDate(2017, 11, 29)
        cases.add(arrayOf(date1, expectedDate1))

        // 31-day month but in the 31th day
        val date2 = DateUtils.getDate(2017, 11, 31)
        val expectedDate2 = DateUtils.getDate(2017, 11, 29)
        cases.add(arrayOf(date2, expectedDate2))

        // 30-day month
        val date3 = DateUtils.getDate(2017, 10, 1)
        val expectedDate3 = DateUtils.getDate(2017, 11, 29)
        cases.add(arrayOf(date3, expectedDate3))

        // February special case in a leap year
        val date4 = DateUtils.getDate(2008, 1, 1)
        val expectedDate4 = DateUtils.getDate(2008, 11, 31)
        cases.add(arrayOf(date4, expectedDate4))

        // February special case in a normal year
        val date5 = DateUtils.getDate(2009, 1, 1)
        val expectedDate5 = DateUtils.getDate(2009, 11, 31)
        cases.add(arrayOf(date5, expectedDate5))

        return cases.iterator()
    }

    /**
     * @return the data to tests the formatDuration method
     */
    @DataProvider
    protected fun getDurationData(): Iterator<Array<Any>> {
        val cases = mutableListOf<Array<Any>>()

        cases.add(arrayOf(500, "500ms"))
        cases.add(arrayOf(1000, "1s, 0ms"))
        cases.add(arrayOf(1500, "1s, 500ms"))
        cases.add(arrayOf(1000 * 60, "1m, 0s, 0ms"))
        cases.add(arrayOf(1000 * 60 + 1500, "1m, 1s, 500ms"))
        cases.add(arrayOf(1000 * 60 * 60, "1h, 0m, 0s, 0ms"))
        cases.add(arrayOf(1000 * 60 * 60 + 1000 * 60 + 1500, "1h, 1m, 1s, 500ms"))

        return cases.iterator()
    }

    @Test(dataProvider = "getDurationData")
    fun formatDuration(duration: Long, expectedResult: String) {
        val result = DateUtils.formatDuration(duration)
        Assert.assertEquals(result, expectedResult)
    }

    @Test
    fun addMonths() {
        Assert.assertEquals(DateUtils.addMonths(DateUtils.getDate(2017, 2, 25), -1), DateUtils.getDate(2017, 1, 25))
    }

    @Test
    fun getLastWeekDayOfPreviousWeek() {

        val friday = DateUtils.getDate(2018, 3, 13)
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(DateUtils.getDate(2018, 3, 15)), friday)
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(DateUtils.getDate(2018, 3, 16)), friday)
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(DateUtils.getDate(2018, 3, 17)), friday)
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(DateUtils.getDate(2018, 3, 18)), friday)
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(DateUtils.getDate(2018, 3, 19)), friday)
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(DateUtils.getDate(2018, 3, 20)), friday)
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(DateUtils.getDate(2018, 3, 21)), friday)

        DateConfiguration.setFakeNow(DateUtils.getDate(2018, 3, 15))
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(), friday)

        DateConfiguration.setFakeNow(DateUtils.getDate(2018, 3, 16))
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(), friday)

        DateConfiguration.setFakeNow(DateUtils.getDate(2018, 3, 17))
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(), friday)

        DateConfiguration.setFakeNow(DateUtils.getDate(2018, 3, 18))
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(), friday)

        DateConfiguration.setFakeNow(DateUtils.getDate(2018, 3, 19))
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(), friday)

        DateConfiguration.setFakeNow(DateUtils.getDate(2018, 3, 20))
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(), friday)

        DateConfiguration.setFakeNow(DateUtils.getDate(2018, 3, 21))
        Assert.assertEquals(DateUtils.getLastWeekDayOfPreviousWeek(), friday)
    }
}
