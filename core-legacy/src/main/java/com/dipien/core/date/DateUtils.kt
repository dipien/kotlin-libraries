package com.dipien.core.date

import com.dipien.core.exception.UnexpectedException
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.TimeUnit

/**
 * Utilities for Dates and Calendars
 */
object DateUtils {

    fun init() {
        // nothing...
    }

    /**
     * @param dateFormatted The formatted string to parse
     * @param dateFormat
     * @return A date that represents the formatted string
     */
    fun parse(dateFormatted: String, dateFormat: String): Date? {
        return parse(dateFormatted, dateFormat, false)
    }

    fun parse(dateFormatted: String, dateFormat: String, useUtc: Boolean): Date? {
        return parse(dateFormatted, SimpleDateFormat(dateFormat), useUtc)
    }

    /**
     * @param dateFormatted The formatted string to parse
     * @param dateFormat
     * @return A date that represents the formatted string
     */
    fun parse(dateFormatted: String, dateFormat: SimpleDateFormat): Date? {
        return parse(dateFormatted, dateFormat, false)
    }

    fun parse(dateFormatted: String, dateFormat: SimpleDateFormat, useUtc: Boolean): Date? {
        var date: Date? = null
        if (dateFormatted.isNotEmpty()) {
            try {
                if (useUtc) {
                    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                }
                date = dateFormat.parse(dateFormatted)
            } catch (e: ParseException) {
                throw UnexpectedException("Error parsing the dateFormatted: " + dateFormatted + " pattern: " + dateFormat.toPattern(), e)
            }
        }
        return date
    }

    /**
     * @param date The [Date] to be formatted
     * @param dateFormat The [DateFormat] used to format the [Date]
     * @return A String that represent the date with the pattern
     */
    fun format(date: Date?, dateFormat: String): String? {
        return format(date, dateFormat, false)
    }

    fun format(date: Date?, dateFormat: String, useUtc: Boolean): String? {
        return format(date, SimpleDateFormat(dateFormat), useUtc)
    }

    /**
     * Transform the [Date] to a [String] using the received [SimpleDateFormat]
     *
     * @param date The [Date] to be formatted
     * @param dateFormat The [DateFormat] used to format the [Date]
     * @return A String that represent the date with the pattern
     */
    fun format(date: Date?, dateFormat: DateFormat): String? {
        return format(date, dateFormat, false)
    }

    fun format(date: Date?, dateFormat: DateFormat, useUtc: Boolean): String? {
        if (useUtc) {
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        }
        return if (date != null) dateFormat.format(date) else null
    }

    fun formatDateTime(date: Date): String? {
        return format(date, DateFormat.getDateTimeInstance())
    }

    fun formatDate(date: Date): String? {
        return format(date, DateFormat.getDateInstance())
    }

    fun formatTime(date: Date): String? {
        return format(date, DateFormat.getTimeInstance(DateFormat.SHORT))
    }

    /**
     * Creates a [Date] for the specified day
     *
     * @param year The year
     * @param monthOfYear The month number (starting on 0)
     * @param dayOfMonth The day of the month
     * @return The [Date]
     */
    fun getDate(year: Int, monthOfYear: Int, dayOfMonth: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, monthOfYear, dayOfMonth)
        truncateTime(calendar)
        return calendar.time
    }

    fun getDate(date: Date, time: Date?, is24Hour: Boolean): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        if (time != null) {
            calendar.set(Calendar.HOUR_OF_DAY, getHour(time, is24Hour))
            calendar.set(Calendar.MINUTE, getMinute(time))
            calendar.set(Calendar.SECOND, 0)
        } else {
            truncateTime(calendar)
        }
        return calendar.time
    }

    fun getDate(date: Date, time: Date): Date {
        return getDate(date, time, true)
    }

    fun getDate(milliseconds: Long): Date {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliseconds
        return calendar.time
    }

    fun getTime(hour: Int, minutes: Int): Date {
        return getTime(hour, minutes, true)
    }

    fun getTime(hour: Int, minutes: Int, is24Hour: Boolean): Date {
        val calendar = Calendar.getInstance()
        calendar.set(if (is24Hour) Calendar.HOUR_OF_DAY else Calendar.HOUR, hour)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 0)
        truncateDate(calendar)
        return calendar.time
    }

    fun getYear(): Int {
        return getYear(now())
    }

    fun getYear(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.YEAR)
    }

    fun getMonth(): Int {
        return getMonth(now())
    }

    fun getMonth(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.MONTH)
    }

    fun getDayOfMonth(date: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun getHour(date: Date, is24Hour: Boolean): Int {
        return getHour(date, TimeZone.getDefault(), is24Hour)
    }

    fun getHour(date: Date, timeZone: TimeZone, is24Hour: Boolean): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.timeZone = timeZone
        return calendar.get(if (is24Hour) Calendar.HOUR_OF_DAY else Calendar.HOUR)
    }

    fun getMinute(date: Date): Int {
        return DateUtils.getMinute(date, TimeZone.getDefault())
    }

    fun getMinute(date: Date, timeZone: TimeZone): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.timeZone = timeZone
        return calendar.get(Calendar.MINUTE)
    }

    fun getSeconds(date: Date): Int {
        return getSeconds(date, TimeZone.getDefault())
    }

    fun getSeconds(date: Date, timeZone: TimeZone): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.timeZone = timeZone
        return calendar.get(Calendar.SECOND)
    }

    fun getDayOfWeek(): DayOfWeek? {
        return getDayOfWeek(now())
    }

    fun getDayOfWeek(date: Date): DayOfWeek {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return DayOfWeek.findByNumber(dayOfWeek)!!
    }

    fun isDateOnWeekend(date: Date): Boolean {
        return getDayOfWeek(date).isWeekend
    }

    fun setHour(date: Date, hours: Int, is24Hour: Boolean): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(if (is24Hour) Calendar.HOUR_OF_DAY else Calendar.HOUR, hours)
        return calendar.time
    }

    fun setMinutes(date: Date, minutes: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.MINUTE, minutes)
        return calendar.time
    }

    fun addSeconds(date: Date, seconds: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.SECOND, seconds)
        return calendar.time
    }

    fun addMinutes(date: Date, minutes: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MINUTE, minutes)
        return calendar.time
    }

    fun addHours(date: Date, hours: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.HOUR, hours)
        return calendar.time
    }

    fun addDays(date: Date, days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, days)
        return calendar.time
    }

    fun addMonths(date: Date, months: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MONTH, months)
        return calendar.time
    }

    fun addYears(date: Date, years: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.YEAR, years)
        return calendar.time
    }

    /**
     * Truncate the date asigning it to 1st of January of 1980
     *
     * @param date The [Date] to truncate
     * @return The truncated [Date]
     */
    fun truncateDate(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        truncateDate(calendar)
        return calendar.time
    }

    /**
     * Truncate the [Calendar] date asigning it to 1st of January of 1980
     *
     * @param calendar The [Calendar] to truncate
     */
    fun truncateDate(calendar: Calendar) {
        calendar.set(Calendar.MONTH, 0)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.YEAR, 1980)
    }

    /**
     * Truncate the date removing hours, minutes, seconds and milliseconds
     *
     * @param date The [Date] to truncate
     * @return The truncated [Date]
     */
    fun truncateTime(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        truncateTime(calendar)
        return calendar.time
    }

    /**
     * Truncate the [Calendar] removing hours, minutes, seconds and milliseconds
     *
     * @param calendar The [Calendar] to truncate
     */
    fun truncateTime(calendar: Calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
    }

    /**
     * @return the current moment
     */
    fun now(): Date {
        return if (DateConfiguration.isFakeNow()) DateConfiguration.getFakeNow()!! else Date()
    }

    fun nowMillis(): Long {
        return if (DateConfiguration.isFakeNow()) DateConfiguration.getFakeNow()!!.time else System.currentTimeMillis()
    }

    /**
     * @param date The date to compare
     * @param startDate The left between' side
     * @param endDate The right between's side
     * @return `true` if the date is in the middle of startDate and endDate
     */
    fun isBetween(date: Date, startDate: Date, endDate: Date): Boolean {
        return isBeforeEquals(startDate, date) && DateUtils.isAfterEquals(endDate, date)
    }

    /**
     * Tests if the date is before than the specified dateToCompare.
     *
     * @param date the date to compare with the dateToCompare.
     * @param dateToCompare the date to compare with the date.
     * @return `true` if the instant of time represented by `date` object is earlier than the
     * instant represented by <tt>dateToCompare</tt>; `false` otherwise.
     */
    fun isBefore(date: Date, dateToCompare: Date): Boolean {
        return date < dateToCompare
    }

    /**
     * Tests if the date is before or equals than the specified dateToCompare.
     *
     * @param date the date to compare with the dateToCompare.
     * @param dateToCompare the date to compare with the date.
     * @return `true` if the instant of time represented by `date` object is earlier or equal than
     * the instant represented by <tt>dateToCompare</tt>; `false` otherwise.
     */
    fun isBeforeEquals(date: Date, dateToCompare: Date): Boolean {
        return date <= dateToCompare
    }

    /**
     * Tests if the date is after or equals than the specified dateToCompare.
     *
     * @param date the date to compare with the dateToCompare.
     * @param dateToCompare the date to compare with the date.
     * @return `true` if the instant of time represented by `date` object is later or equal than
     * the instant represented by <tt>dateToCompare</tt>; `false` otherwise.
     */
    fun isAfterEquals(date: Date, dateToCompare: Date): Boolean {
        return date >= dateToCompare
    }

    /**
     * Tests if the date is after than the specified dateToCompare.
     *
     * @param date the date to compare with the dateToCompare.
     * @param dateToCompare the date to compare with the date.
     * @return `true` if the instant of time represented by `date` object is later than the
     * instant represented by <tt>dateToCompare</tt>; `false` otherwise.
     */
    fun isAfter(date: Date, dateToCompare: Date): Boolean {
        return date > dateToCompare
    }

    /**
     * Returns true if two periods overlap
     *
     * @param startDate1 the period one start date
     * @param endDate1 the period one end date
     * @param startDate2 the period two start date
     * @param endDate2 the period two end date
     * @return true if overlap
     */
    fun periodsOverlap(startDate1: Date, endDate1: Date, startDate2: Date, endDate2: Date): Boolean {
        return ((startDate1.before(endDate2) || startDate1 == endDate2) && (endDate1.after(startDate2) || endDate1 == startDate2))
    }

    /**
     * Returns true if the first period contains the second periods
     *
     * @param startDate1 the period one start date
     * @param endDate1 the period one end date
     * @param startDate2 the period two start date
     * @param endDate2 the period two end date
     * @return true if the first period contains the second period
     */
    fun containsPeriod(startDate1: Date, endDate1: Date, startDate2: Date, endDate2: Date): Boolean {
        return isBeforeEquals(startDate1, startDate2) && DateUtils.isAfterEquals(endDate1, endDate2)
    }

    fun isToday(date: Date): Boolean {
        return truncateTime(date) == today()
    }

    fun isToday(timestamp: Long): Boolean {
        return isToday(getDate(timestamp))
    }

    fun isYesterdayOrPrevious(date: Date): Boolean {
        return isYesterdayOrPrevious(date.time)
    }

    fun isYesterdayOrPrevious(timestamp: Long): Boolean {
        return timestamp < today().time
    }

    fun isSameDay(a: Date, b: Date): Boolean {
        return truncateTime(a) == truncateTime(b)
    }

    /**
     * @return a day after today
     */
    fun tomorrow(): Date {
        return addDays(today(), 1)
    }

    fun today(): Date {
        return truncateTime(now())
    }

    /**
     * @return a day before today
     */
    fun yesterday(): Date {
        return addDays(today(), -1)
    }

    /**
     * @param months amount of months to move the calendar
     * @return a date that is `months` in the future/past. Use negative values for past dates.
     */
    fun monthsAway(months: Int): Date {
        return addMonths(today(), months)
    }

    /**
     * @return a date that is one month in the future
     */
    fun oneMonthInFuture(): Date {
        return monthsAway(1)
    }

    /**
     * @return a date that is one month in the past
     */
    fun oneMonthInPast(): Date {
        return monthsAway(-1)
    }

    fun getLastWeekDayOfPreviousWeek(): Date {
        return getLastWeekDayOfPreviousWeek(now())
    }

    fun getLastWeekDayOfPreviousWeek(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val diff = -(dayOfWeek + 1)
        return addDays(date, diff)
    }

    fun isLastWeekDayOfWeek(): Boolean {
        return getDayOfWeek() == DayOfWeek.FRIDAY
    }

    /**
     * @param date Date that includes the desired month in order to calculate the last day of that month
     * @return the date of the last day of the month
     */
    fun getLastDayOfMonth(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        truncateTime(calendar)
        return calendar.time
    }

    fun isLastWeekDayOfMonth(): Boolean {
        return today() == getLastWeekDayOfMonth()
    }

    fun getLastWeekDayOfMonth(): Date {
        return getLastWeekDayOfMonth(now())
    }

    fun getLastWeekDayOfMonth(date: Date): Date {
        val lastDayOfMonth = getLastDayOfMonth(date)
        val dayOfWeek = getDayOfWeek(lastDayOfMonth)

        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return addDays(lastDayOfMonth, -1)
        } else return if (dayOfWeek == DayOfWeek.SUNDAY) {
            addDays(lastDayOfMonth, -2)
        } else {
            lastDayOfMonth
        }
    }

    fun getLastDayOfYear(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.MONTH, 11)
        calendar.set(Calendar.DAY_OF_MONTH, 31)
        truncateTime(calendar)
        return calendar.time
    }

    fun isLastWeekDayOfYear(): Boolean {
        return today() == getLastWeekDayOfYear()
    }

    fun getLastWeekDayOfYear(): Date {
        return getLastWeekDayOfYear(now())
    }

    fun getLastWeekDayOfYear(date: Date): Date {
        val lastDayOfYear = getLastDayOfYear(date)
        return when (getDayOfWeek(lastDayOfYear)) {
            DayOfWeek.SATURDAY -> addDays(lastDayOfYear, -1)
            DayOfWeek.SUNDAY -> addDays(lastDayOfYear, -2)
            else -> lastDayOfYear
        }
    }

    /**
     * @param fromDate the start date
     * @param toDate the end date
     * @return an integer representing the amount of days between fromDate and toDate
     */
    fun differenceInDays(fromDate: Date, toDate: Date): Int {
        var diff: Long = toDate.time - fromDate.time
        diff /= (TimeUnit.DAYS.toMillis(1))
        return diff.toInt()
    }

    /**
     * @param fromDate the start date
     * @param toDate the end date
     * @return an double representing the amount of hours between fromDate and toDate
     */
    fun differenceInHours(fromDate: Date, toDate: Date): Double {
        var diff = (toDate.time - fromDate.time).toDouble()
        diff /= (TimeUnit.HOURS.toMillis(1))
        return diff
    }

    /**
     * @param fromDate the start date
     * @param toDate the end date
     * @return an integer representing the amount of minutes between fromDate and toDate
     */
    fun differenceInMinutes(fromDate: Date, toDate: Date): Int {
        var diff: Long = toDate.time - fromDate.time
        diff /= (TimeUnit.MINUTES.toMillis(1))
        return diff.toInt()
    }

    fun formatDuration(duration: Long): String {

        val hours = TimeUnit.MILLISECONDS.toHours(duration)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - (hours * 60)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - (hours * 60 * 60) - (minutes * 60)
        val milliseconds = (TimeUnit.MILLISECONDS.toMillis(duration) - (hours * 60 * 60 * 1000) - (minutes * 60 * 1000) - (seconds * 1000))

        val builder = StringBuilder()
        if (hours > 0) {
            builder.append(hours)
            builder.append("h, ")
        }
        if ((minutes > 0) || (builder.isNotEmpty())) {
            builder.append(minutes)
            builder.append("m, ")
        }
        if ((seconds > 0) || (builder.isNotEmpty())) {
            builder.append(seconds)
            builder.append("s, ")
        }
        if (milliseconds >= 0) {
            builder.append(milliseconds)
            builder.append("ms")
        }

        return builder.toString()
    }
}
