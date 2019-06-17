package com.jdroid.java.date

import com.jdroid.java.collections.Lists

enum class DayOfWeek(val displayName: String, val number: Int, val isWeekend: Boolean) {

    SUNDAY("Sunday", 1, true),
    MONDAY("Monday", 2, false),
    TUESDAY("Tuesday", 3, false),
    WEDNESDAY("Wednesday", 4, false),
    THURSDAY("Thursday", 5, false),
    FRIDAY("Friday", 6, false),
    SATURDAY("Saturday", 7, true);

    fun getNextDay(): DayOfWeek? {
        return findByNumber(number % 7 + 1)
    }

    override fun toString(): String {
        return displayName
    }

    companion object {

        fun findByNumber(number: Int): DayOfWeek? {
            for (each in values()) {
                if (each.number == number) {
                    return each
                }
            }
            return null
        }

        fun findByName(displayName: String): DayOfWeek? {
            var dayOfWeek: DayOfWeek? = null
            for (each in values()) {
                if (each.displayName.equals(displayName, ignoreCase = true)) {
                    dayOfWeek = each
                    break
                }
            }
            return dayOfWeek
        }

        fun getWeekDays(): List<DayOfWeek> {
            val weekDays = Lists.newArrayList<DayOfWeek>()
            for (each in values()) {
                if (!each.isWeekend) {
                    weekDays.add(each)
                }
            }
            return weekDays
        }
    }
}