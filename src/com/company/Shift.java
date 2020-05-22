package com.company;

import java.time.LocalTime;
import java.util.Locale;

public class Shift {

    public int idShift;
    public double [] duration;
    public int shiftCat;
    public String shiftName;
    public LocalTime startTime;
    public LocalTime endTime;
    public String competence;


    public Shift(int id_shift, double [] shift_duration, int  shift_cat, String shift_name,
                 LocalTime start, LocalTime end, String shift_competence) {
        idShift = id_shift;
        duration = shift_duration;
        shiftCat = shift_cat;
        startTime = start;
        endTime = end;
        shiftName = shift_name;
        competence = shift_competence;
    }

    public int getIdShift() {
        return idShift;
    }

    public double getDuration(int i) {
        return duration [i];
    }
    public int getShiftCat(){
        return  shiftCat;
    }
    public String getShiftName() {
        return shiftName;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public String getCompetence(){
        return  competence;
    }
}