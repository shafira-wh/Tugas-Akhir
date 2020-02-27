package com.company;

public class Shift {

    public int idShift;
    public double mon;
    public double tue;
    public double wed;
    public double thur;
    public double fri;
    public double sat;
    public double sun;
    public int shiftCat;
    public String shiftName;
    public int startHour;
    public int startMin;
    public int endHour;
    public int endMin;
    public String competence;


    public Shift(int id_shift, double monday, double tuesday, double wednesday, double thursday,
                 double friday, double saturday, double sunday, int  shift_cat, String shift_name,
                 int  start_hour, int start_min, int end_hour, int end_min, String s_competence) {
        idShift = id_shift;
        mon = monday;
        tue = tuesday;
        wed = wednesday;
        thur = thursday;
        fri = friday;
        sat = saturday;
        sun = sunday;
        shiftCat = shift_cat;
        startHour = start_hour;
        startMin = start_min;
        endHour = end_hour;
        endMin = end_min;
        shiftName = shift_name;
        competence = s_competence;
    }

    public int getIdShift() {
        return idShift;
    }
    public double getMon() {
        return mon;
    }
    public double getTue() {
        return tue;
    }
    public double getWed() {
        return wed;
    }
    public double getThur() {
        return thur;
    }
    public double getFri() {
        return fri;
    }
    public double getSat() {
        return sat;
    }
    public double getSun() {
        return sun;
    }
    public int getShiftCat(){
    return  shiftCat;
    }
    public String getShiftName() {
        return shiftName;
    }
    public int getStartHour() {
        return startHour;
    }

    public int getStartMin() {
        return startMin;
    }
    public int getEndHour() {
        return endHour;
    }
    public int getEndMin() {
        return  endMin;
    }

    public String getCompetence(){
        return  competence;
    }
}
