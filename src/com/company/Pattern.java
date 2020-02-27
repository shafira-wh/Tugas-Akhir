package com.company;

public class Pattern {
    public int idPattern;
    public String startDay;
    public String wantedPattern;

    public Pattern(int id_pattern, String starts_day, String wanted_pattern){
        idPattern = id_pattern;
        startDay = starts_day;
        wantedPattern = wanted_pattern;
    }

    public int getIdPattern() {
        return idPattern;
    }

    public String getStartDay() {
        return startDay;
    }

    public String getWantedPattern() {
        return wantedPattern;
    }
}


