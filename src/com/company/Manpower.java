package com.company;

public class Manpower {
    public String manShift;
    public int manMon;
    public int manTue;
    public int manWed;
    public int manThur;
    public int manFri;
    public int manSat;
    public int manSun;
    public int idMan;

    public Manpower (String man_shift, int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday, int id_man) {
        manShift = man_shift;
        manMon = monday;
        manTue = tuesday;
        manWed = wednesday;
        manThur = thursday;
        manFri = friday;
        manSat = saturday;
        manSun = sunday;
        idMan = id_man;
    }

    public String getManShift() {
        return manShift;
}
    public int getManMon () {
        return manMon;
    }

    public int getManTue() {
        return manTue;
    }
    public int getManWed() {
        return manWed;
    }
    public int getManThur() {
        return manThur;
    }
    public int getManFri() {
        return manFri;
    }
    public int getManSat() {
        return manSat;
    }
    public int getManSun() {
        return manSun;
    }
    public int getIdMan() {
        return idMan;
    }

}
