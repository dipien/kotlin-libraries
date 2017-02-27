package com.jdroid.java.date;

import com.jdroid.java.collections.Lists;

import java.util.List;

public enum DayOfWeek {

	SUNDAY("Sunday", 1, true),
	MONDAY("Monday", 2, false),
	TUESDAY("Tuesday", 3, false),
	WEDNESDAY("Wednesday", 4, false),
	THURSDAY("Thursday", 5, false),
	FRIDAY("Friday", 6, false),
	SATURDAY("Saturday", 7, true);

	private String name;
	private int number;
	private Boolean weekend;

	private DayOfWeek(String name, int number, Boolean weekend) {
		this.name = name;
		this.number = number;
		this.weekend = weekend;
	}

	public Boolean isWeekend() {
		return weekend;
	}

	public static DayOfWeek findByNumber(int number) {
		for (DayOfWeek each : values()) {
			if (each.getNumber() == number) {
				return each;
			}
		}
		return null;
	}

	public static DayOfWeek findByName(String name) {
		DayOfWeek dayOfWeek = null;
		for (DayOfWeek each : values()) {
			if (each.name().equalsIgnoreCase(name)) {
				dayOfWeek = each;
				break;
			}
		}
		return dayOfWeek;
	}

	public DayOfWeek getNextDay() {
		return findByNumber((number % 7) + 1);
	}

	public static List<DayOfWeek> getWeekDays() {
		List<DayOfWeek> weekDays = Lists.newArrayList();
		for (DayOfWeek each : DayOfWeek.values()) {
			if (!each.isWeekend()) {
				weekDays.add(each);
			}
		}
		return weekDays;
	}

	@Override
	public String toString() {
		return name;
	}

	public int getNumber() {
		return number;
	}
}