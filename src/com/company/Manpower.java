package com.company;

public class Manpower {
    public String manShift;
    public int [] shiftNeeded;
    public int idMan;

    public Manpower (String man_shift, int [] shift_needed , int id_man) {
        manShift = man_shift;
    shiftNeeded = shift_needed;
        idMan = id_man;
    }

    public String getManShift() {
        return manShift;
}

    public int  getShiftNeeded(int i) {
        return shiftNeeded[i];
    }

    public int getIdMan() {
        return idMan;
    }

}
