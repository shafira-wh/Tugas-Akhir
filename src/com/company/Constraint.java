package com.company;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class Constraint {

    public String[] consValue;
    public boolean hc1;
    public boolean hc2;
    public double hc3;
    public boolean hc4;
    public LocalTime hc5_1;
    public LocalTime hc5_2;
    public LocalTime hc5_3;
    public LocalTime hc5_4;
    public LocalTime hc5_5;
    public LocalTime hc5_6;
    public int hc6;
    public int hc7;


    public Constraint(String[] constraintValue) {
        consValue = constraintValue;
    }
    // HARD CONSTRAINT

    public void setHc1() {
        if (consValue[0].equals("Yes")) {
            hc1 = true;
        }
        if (consValue[0].equals("No")) {
            hc1 = false;
        }
    }

    public boolean getHc1() {
        return hc1;
    }

    public void setHc2() {
        if (consValue[1].equals("Yes")) {
            hc2 = true;
        }
        if (consValue[1].equals("No")) {
            hc2 = false;
        }
    }

    public boolean getHc2() {
        return hc2;
    }

    public void setHc3() {
        hc3 = Double.parseDouble(consValue[2].substring(1, 2));
    }

    public double getHc3() {
        return hc3 / 100;
    }

    public void setHc4() {
        if (consValue[3].equals("Yes")) {
            hc4 = true;
        }
        if (consValue[3].equals("No")) {
            hc4 = false;
        }
    }
    public boolean getHc4() {
        return hc4;
    }

    public void  setHc5_1()  {
        String time = consValue[4] + ":00";
        hc5_1 = LocalTime.parse(time);
    }
    public LocalTime getHc5_1() {
        return hc5_1;
    }

    public void  setHc5_2()  {
        String time = consValue[5] + ":00";
        hc5_2 = LocalTime.parse(time);
    }
    public LocalTime getHc5_2() {
        return hc5_2;
    }

    public void  setHc5_3()  {
        String time = consValue[6] + ":00";
        hc5_3 = LocalTime.parse(time);
    }
    public LocalTime getHc5_3() {
        return hc5_3;
    }
    public void  setHc5_4()  {
        String time = consValue[7] + ":00";
        hc5_4 = LocalTime.parse(time);
    }
    public LocalTime getHc5_4() {
        return hc5_4;
    }

    public void  setHc5_5()  {
        String time = consValue[8] + ":00";
        hc5_5 = LocalTime.parse(time);
    }
    public LocalTime getHc5_5() {
        return hc5_5;
    }
    public void  setHc5_6()  {
        String time = consValue[9] + ":00";
        hc5_6 = LocalTime.parse(time);
    }
    public LocalTime getHc5_6() {
        return hc5_6;
    }

    public void setHc6() {
        String hardCons6 = consValue [10];
        hc6 = Integer.parseInt(hardCons6);
    }
    public int getHc6() {
        return hc6;
    }
    public void setHc7() {
        String hardCons7 = consValue [11];
        hc7 = Integer.parseInt(hardCons7);
    }
    public int getHc7() {
        return hc7;
    }
}



