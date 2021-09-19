package com.dipien.core.utils

import com.dipien.core.date.DateConfiguration
import com.dipien.core.date.DateTimeFormat
import com.dipien.core.date.DateUtils
import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.util.Calendar
import java.util.Date

/**
 * Test class for the [DateUtils] class.
 */
class DateUtilsTest {

    @After
    fun onAfterMethod() {
        DateConfiguration.setFakeNow(null)
    }

    @Test
    fun formatData() {
        format(DateUtils.getDate(2010, Calendar.AUGUST, 10), DateTimeFormat.MMDDYYYY, "08/10/2010")
        format(DateUtils.getDate(2010, Calendar.AUGUST, 10), DateTimeFormat.MMDDYYYY_SLASH, "08-10-2010")
        format(DateUtils.getTime(5, 30, true), DateTimeFormat.HHMMAA, "05:30 AM")
    }

    /**
     * @param date The [Date] to format
     * @param pattern The pattern to format the [Date]
     * @param expected The expected string
     */
    private fun format(date: Date, pattern: String, expected: String) {
        val result = DateUtils.format(date, pattern)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun getForIsBetween() {
        val calendar = Calendar.getInstance()
        calendar.set(2009, Calendar.JANUARY, 20)
        val date = calendar.time

        calendar.set(2009, Calendar.JANUARY, 20)
        var startDate = calendar.time
        calendar.set(2009, Calendar.JANUARY, 20)
        var endDate = calendar.time
        isBetween(date, startDate, endDate, true)

        calendar.set(2009, Calendar.JANUARY, 19)
        startDate = calendar.time
        calendar.set(2009, Calendar.JANUARY, 21)
        endDate = calendar.time
        isBetween(date, startDate, endDate, true)

        calendar.set(2009, Calendar.JANUARY, 19)
        startDate = calendar.time
        calendar.set(2009, Calendar.JANUARY, 19)
        endDate = calendar.time
        isBetween(date, startDate, endDate, false)
    }

    /**
     * Test method for the [DateUtils.isBetween] method.
     *
     * @param date The date
     * @param startDate The start date
     * @param endDate The end date
     * @param expectedResult The expected result
     */
    private fun isBetween(date: Date, startDate: Date, endDate: Date, expectedResult: Boolean) {
        Assert.assertEquals(expectedResult, DateUtils.isBetween(date, startDate, endDate))
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
     */
    @Test
    fun periodsOverlapData() {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.JANUARY
        periodsOverlapDateTest(DateUtils.getDate(year, month, 10), DateUtils.getDate(year, month, 20), true)
        periodsOverlapDateTest(DateUtils.getDate(year, month, 12), DateUtils.getDate(year, month, 18), true)
        periodsOverlapDateTest(DateUtils.getDate(year, month, 10), DateUtils.getDate(year, month, 12), true)
        periodsOverlapDateTest(DateUtils.getDate(year, month, 18), DateUtils.getDate(year, month, 20), true)
        periodsOverlapDateTest(DateUtils.getDate(year, month, 8), DateUtils.getDate(year, month, 10), true)
        periodsOverlapDateTest(DateUtils.getDate(year, month, 8), DateUtils.getDate(year, month, 12), true)
        periodsOverlapDateTest(DateUtils.getDate(year, month, 20), DateUtils.getDate(year, month, 22), true)
        periodsOverlapDateTest(DateUtils.getDate(year, month, 18), DateUtils.getDate(year, month, 22), true)
        periodsOverlapDateTest(DateUtils.getDate(year, month, 8), DateUtils.getDate(year, month, 22), true)
        periodsOverlapDateTest(DateUtils.getDate(year, month, 6), DateUtils.getDate(year, month, 8), false)
        periodsOverlapDateTest(DateUtils.getDate(year, month, 22), DateUtils.getDate(year, month, 24), false)
    }

    /**
     * @param start2 The second rage start date
     * @param end2 The second rage end date
     * @param expected the expected overlap result between the current rage and the scenario
     */
    private fun periodsOverlapDateTest(start2: Date, end2: Date, expected: Boolean) {

        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.JANUARY

        val start = DateUtils.getDate(year, month, 10)
        val end = DateUtils.getDate(year, month, 20)

        Assert.assertEquals(expected, DateUtils.periodsOverlap(start, end, start2, end2))
    }

    @Test
    fun isAfterEquals() {
        isAfterEquals(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 11, true)
        isAfterEquals(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 12, false)
        isAfterEquals(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 10, true)
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
    private fun isAfterEquals(
        year: Int,
        month: Int,
        date: Int,
        yearToCompare: Int,
        monthToCompare: Int,
        dateToCompare: Int,
        expectedResult: Boolean
    ) {
        Assert.assertEquals(
            expectedResult,
            DateUtils.isAfterEquals(
                DateUtils.getDate(year, month, date),
                DateUtils.getDate(yearToCompare, monthToCompare, dateToCompare)
            )
        )
    }

    @Test
    fun isAfter() {
        isAfter(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 11, false)
        isAfter(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 12, false)
        isAfter(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 10, true)
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
    private fun isAfter(
        year: Int,
        month: Int,
        date: Int,
        yearToCompare: Int,
        monthToCompare: Int,
        dateToCompare: Int,
        expectedResult: Boolean
    ) {
        Assert.assertEquals(
            expectedResult,
            DateUtils.isAfter(
                DateUtils.getDate(year, month, date),
                DateUtils.getDate(yearToCompare, monthToCompare, dateToCompare)
            )
        )
    }

    @Test
    fun isBeforeEquals() {
        isBeforeEquals(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 11, true)
        isBeforeEquals(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 12, true)
        isBeforeEquals(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 10, false)
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
    private fun isBeforeEquals(
        year: Int,
        month: Int,
        date: Int,
        yearToCompare: Int,
        monthToCompare: Int,
        dateToCompare: Int,
        expectedResult: Boolean
    ) {
        Assert.assertEquals(
            expectedResult,
            DateUtils.isBeforeEquals(
                DateUtils.getDate(year, month, date),
                DateUtils.getDate(yearToCompare, monthToCompare, dateToCompare)
            )
        )
    }

    fun isBefore() {
        isBefore(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 11, false)
        isBefore(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 12, true)
        isBefore(2009, Calendar.JANUARY, 11, 2009, Calendar.JANUARY, 10, false)
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
    private fun isBefore(
        year: Int,
        month: Int,
        date: Int,
        yearToCompare: Int,
        monthToCompare: Int,
        dateToCompare: Int,
        expectedResult: Boolean
    ) {
        Assert.assertEquals(
            expectedResult,
            DateUtils.isBefore(
                DateUtils.getDate(year, month, date),
                DateUtils.getDate(yearToCompare, monthToCompare, dateToCompare)
            )
        )
    }

    private fun lastDayOfTheMonth(date: Date, expectedResult: Date) {
        val lastDay = DateUtils.getLastDayOfMonth(date)
        Assert.assertEquals(expectedResult, lastDay)
    }

    @Test
    fun lastDayOfTheMonth() {
        // 31-day month
        val date1 = DateUtils.getDate(2010, 0, 10)
        val expectedDate1 = DateUtils.getDate(2010, 0, 31)
        lastDayOfTheMonth(date1, expectedDate1)

        // 31-day month but in the 31th day
        val date2 = DateUtils.getDate(2010, 0, 31)
        val expectedDate2 = DateUtils.getDate(2010, 0, 31)
        lastDayOfTheMonth(date2, expectedDate2)

        // 30-day month
        val date3 = DateUtils.getDate(2010, 3, 1)
        val expectedDate3 = DateUtils.getDate(2010, 3, 30)
        lastDayOfTheMonth(date3, expectedDate3)

        // February special case in a leap year
        val date4 = DateUtils.getDate(2008, 1, 1)
        val expectedDate4 = DateUtils.getDate(2008, 1, 29)
        lastDayOfTheMonth(date4, expectedDate4)

        // February special case in a normal year
        val date5 = DateUtils.getDate(2009, 1, 1)
        val expectedDate5 = DateUtils.getDate(2009, 1, 28)
        lastDayOfTheMonth(date5, expectedDate5)
    }

    private fun lastWeekDayOfTheMonth(date: Date, expectedResult: Date) {
        val lastDay = DateUtils.getLastWeekDayOfMonth(date)
        Assert.assertEquals(expectedResult, lastDay)
    }

    @Test
    fun lastWeekDayOfTheMonth() {
        // 31-day month
        val date1 = DateUtils.getDate(2017, 11, 1)
        val expectedDate1 = DateUtils.getDate(2017, 11, 29)
        lastWeekDayOfTheMonth(date1, expectedDate1)

        // 31-day month but in the 31th day
        val date2 = DateUtils.getDate(2017, 11, 31)
        val expectedDate2 = DateUtils.getDate(2017, 11, 29)
        lastWeekDayOfTheMonth(date2, expectedDate2)

        // 30-day month
        val date3 = DateUtils.getDate(2017, 10, 1)
        val expectedDate3 = DateUtils.getDate(2017, 10, 30)
        lastWeekDayOfTheMonth(date3, expectedDate3)

        // February special case in a leap year
        val date4 = DateUtils.getDate(2008, 1, 1)
        val expectedDate4 = DateUtils.getDate(2008, 1, 29)
        lastWeekDayOfTheMonth(date4, expectedDate4)

        // February special case in a normal year
        val date5 = DateUtils.getDate(2009, 1, 1)
        val expectedDate5 = DateUtils.getDate(2009, 1, 27)
        lastWeekDayOfTheMonth(date5, expectedDate5)
    }

    /**
     * @param date the date to be tested
     * @param expectedResult The expected result
     */
    private fun lastDayOfTheYear(date: Date, expectedResult: Date) {
        val lastDay = DateUtils.getLastDayOfYear(date)
        Assert.assertEquals(expectedResult, lastDay)
    }

    @Test
    fun lastDayOfTheYear() {
        // 31-day month
        val date1 = DateUtils.getDate(2010, 0, 10)
        val expectedDate1 = DateUtils.getDate(2010, 11, 31)
        lastDayOfTheYear(date1, expectedDate1)

        // 31-day month but in the 31th day
        val date2 = DateUtils.getDate(2010, 0, 31)
        val expectedDate2 = DateUtils.getDate(2010, 11, 31)
        lastDayOfTheYear(date2, expectedDate2)

        // 30-day month
        val date3 = DateUtils.getDate(2010, 3, 1)
        val expectedDate3 = DateUtils.getDate(2010, 11, 31)
        lastDayOfTheYear(date3, expectedDate3)

        // February special case in a leap year
        val date4 = DateUtils.getDate(2008, 1, 1)
        val expectedDate4 = DateUtils.getDate(2008, 11, 31)
        lastDayOfTheYear(date4, expectedDate4)

        // February special case in a normal year
        val date5 = DateUtils.getDate(2009, 1, 1)
        val expectedDate5 = DateUtils.getDate(2009, 11, 31)
        lastDayOfTheYear(date5, expectedDate5)
    }

    private fun lastWeekDayOfTheYear(date: Date, expectedResult: Date) {
        val lastDay = DateUtils.getLastWeekDayOfYear(date)
        Assert.assertEquals(expectedResult, lastDay)
    }

    @Test
    fun lastWeekDayOfTheYear() {
        // 31-day month
        val date1 = DateUtils.getDate(2017, 11, 1)
        val expectedDate1 = DateUtils.getDate(2017, 11, 29)
        lastWeekDayOfTheYear(date1, expectedDate1)

        // 31-day month but in the 31th day
        val date2 = DateUtils.getDate(2017, 11, 31)
        val expectedDate2 = DateUtils.getDate(2017, 11, 29)
        lastWeekDayOfTheYear(date2, expectedDate2)

        // 30-day month
        val date3 = DateUtils.getDate(2017, 10, 1)
        val expectedDate3 = DateUtils.getDate(2017, 11, 29)
        lastWeekDayOfTheYear(date3, expectedDate3)

        // February special case in a leap year
        val date4 = DateUtils.getDate(2008, 1, 1)
        val expectedDate4 = DateUtils.getDate(2008, 11, 31)
        lastWeekDayOfTheYear(date4, expectedDate4)

        // February special case in a normal year
        val date5 = DateUtils.getDate(2009, 1, 1)
        val expectedDate5 = DateUtils.getDate(2009, 11, 31)
        lastWeekDayOfTheYear(date5, expectedDate5)
    }

    @Test
    fun formatDuration() {
        formatDuration(500, "500ms")
        formatDuration(1000, "1s, 0ms")
        formatDuration(1500, "1s, 500ms")
        formatDuration(1000 * 60, "1m, 0s, 0ms")
        formatDuration(1000 * 60 + 1500, "1m, 1s, 500ms")
        formatDuration(1000 * 60 * 60, "1h, 0m, 0s, 0ms")
        formatDuration(1000 * 60 * 60 + 1000 * 60 + 1500, "1h, 1m, 1s, 500ms")
    }

    private fun formatDuration(duration: Long, expectedResult: String) {
        val result = DateUtils.formatDuration(duration)
        Assert.assertEquals(expectedResult, result)
    }

    @Test
    fun addMonths() {
        Assert.assertEquals(DateUtils.getDate(2017, 1, 25), DateUtils.addMonths(DateUtils.getDate(2017, 2, 25), -1))
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
