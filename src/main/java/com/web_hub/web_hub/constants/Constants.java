package com.web_hub.web_hub.constants;



public final class Constants {

    private Constants() {}

    // Yearly Max Allocations
    public static final int MAX_ANNUAL_LEAVE_DAYS = 21;
    public static final int MAX_SICK_LEAVE_DAYS = 10;
    public static final int MAX_EARNED_LEAVE_DAYS = 18;
    public static final int MAX_COMPASSIONATE_LEAVE_DAYS = 7;
    public static final int MAX_MATERNITY_LEAVE_DAYS = 90;
    public static final int MAX_PATERNITY_LEAVE_DAYS = 14;

    // Fallback default value for any unmapped leave types
    public static final int DEFAULT_MAX_LEAVE_DAYS = 14;
}
