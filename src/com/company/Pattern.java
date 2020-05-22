package com.company;

public class Pattern {
    public String startDay;
    public String shiftPattern;

    public Pattern(String starts_day, String shift_pattern){

        startDay = starts_day;
        shiftPattern = shift_pattern;
    }


    public String getStartDay() {
        return startDay;
    }

    public String getShiftPattern() {
        return shiftPattern;
    }
}


