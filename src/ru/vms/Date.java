package ru.vms;

import java.util.Calendar;

/**
 * A class that represents a specific date (year, month, day)
 * This class provides methods for creating a date, validating it, and comparing it to other dates.
 * It includes logic for leap year calculations.
 * @author Simeon Thomas
 * @author Reeham Anwar
 */
public class Date implements Comparable<Date>{
    private int year;
    private int day;
    private int month;

    public static final int FOUR_YEAR_CYCLE = 4;
    public static final int HUNDRED_YEARS_EXCEPTION = 100;
    public static final int FOUR_HUNDRED_YEARS_RULE = 400;

    /**
     * Constructs a date with month/day/year components.
     * @param month the month (1–12)
     * @param day   the day of month
     * @param year  the year (> 0)
     */
    public Date(int month, int day, int year){
        this.year = year;
        this.day = day;
        this.month = month;
    }

    /**
     * Parses a mm/dd/yyyy string into a Date.
     * @param mmddyyyy the date string to parse
     * @return a Date instance from the parsed components
     */
    public static Date fromString(String mmddyyyy) {
        String[] parts = mmddyyyy.split("/");
        int m = Integer.parseInt(parts[0]);
        int d = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        return new Date(m, d, y);
    }

    /**
     * Checks if this date is a valid calendar date.
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        if (year <= 0) return false;
        if (month < 1 || month > 12) return false;
        int maxDay = daysInMonth(month, year);
        return day >= 1 && day <= maxDay;
    }

    private static int daysInMonth(int m, int y) {
        switch (m) {
            case Calendar.JANUARY + 1:
            case Calendar.MARCH + 1:
            case Calendar.MAY + 1:
            case Calendar.JULY + 1:
            case Calendar.AUGUST + 1:
            case Calendar.OCTOBER + 1:
            case Calendar.DECEMBER + 1:
                return 31;
            case Calendar.APRIL + 1:
            case Calendar.JUNE + 1:
            case Calendar.SEPTEMBER + 1:
            case Calendar.NOVEMBER + 1:
                return 30;
            case Calendar.FEBRUARY + 1:
                return isLeap(y) ? 29 : 28;
            default:
                return 0;
        }
    }

    /**
     * Determines whether a year is a leap year.
     * @param y the year to check
     * @return true if the year is leap, false otherwise
     */
    public static boolean isLeap(int y) {
        if (y % FOUR_YEAR_CYCLE != 0) {
            return false;
        }
        if (y % HUNDRED_YEARS_EXCEPTION != 0) {
            return true;
        }
        return (y % FOUR_HUNDRED_YEARS_RULE == 0);
    }

    /**
     * Compares this date to another by year, then month, then day.
     * @param o the other date
     * @return a negative, zero, or positive value per the Comparable
     */
    @Override
    public int compareTo(Date o) {
        if (this.year != o.year) {
            return this.year - o.year;
        }
        if (this.month != o.month) {
            return this.month - o.month;
        }
        return this.day - o.day;
    }

    /**
     * Checks equality with another object.
     * Two dates are equal if year, month, and day are the same.
     * @param obj the other object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Date)) {
            return false;
        }
        Date d = (Date) obj;
        return this.year == d.year && this.month == d.month && this.day == d.day;
    }

    /**
     * Returns the date in m/d/yyyy format.
     * @return the string representation of this date
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Gets the year component.
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the month component.
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Gets the day of month component.
     * @return the day
     */
    public int getDay() {
        return day;
    }

    public static void main(String[] args) {
        System.out.println(new Date(1, 1, 0).isValid());
        System.out.println(new Date(13, 19, 2000).isValid());
        System.out.println(new Date(2, 29, 1999).isValid());
        System.out.println(new Date(6, 31, 2000).isValid());
        System.out.println(new Date(2, 29, 2024).isValid());
        System.out.println(new Date(12, 31, 2025).isValid());
    }
}
