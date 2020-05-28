package com.company;

public class Pattern {
    public int startDay;
    public String [] shiftPattern;

    public Pattern(int starts_day, String [] shift_pattern){

        startDay = starts_day;
        shiftPattern = shift_pattern;
    }


    public int getStartDay() {
        return startDay;
    }

    public String [] getShiftPattern() {
        return shiftPattern;
    }
}


