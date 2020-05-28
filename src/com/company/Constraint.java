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
    public int sc1_1;
    public int sc1_2;
    public int sc1_3;
    public int sc2;
    public int sc3_1;
    public int sc3_2;
    public int sc3_3;
    public int sc4;
    public int sc5_1Max;
    public int sc5_1Min;
    public int sc5_2Max;
    public int sc5_2Min;
    public int sc5_3Max;
    public int sc5_3Min;
    public boolean sc6;
    public boolean sc7;
    public int sc8;
    public int sc9;




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
    //SOFT CONSTRAINT

    public void setSc1_1(){
        if (consValue[0].equals("N/A"))
            sc1_1=0;
        else
            sc1_1=Integer.parseInt(consValue[0]);
    }

    public int getSc1_1() {
        return sc1_1;
    }

    public void setSc1_2() {
        if (consValue[1].equals("N/A"))
            sc1_2 = 0;
        else
            sc1_2 = Integer.parseInt(consValue[1]);
    }

    public int getSc1_2() {
        return sc1_2;
    }

    public void setSc1_3() {
        if (consValue[2].equals("N/A"))
            sc1_3 = 0;
        else
            sc1_3 = Integer.parseInt(consValue[2]);
    }

    public int getSc1_3() {
        return sc1_3;
    }

    public void setSc2() {
        if (consValue[3].equals("N/A"))
            sc2 = 0;
        else
            sc2 = Integer.parseInt(consValue[3]);
    }

    public int getSc2() {
        return sc2;
    }

    public void setSc3_1() {
        if (consValue[4].equals("N/A"))
            sc3_1 = 0;
        else
            sc3_1 = Integer.parseInt(consValue[4]);
    }

    public int getSc3_1() {
        return sc3_1;
    }

    public void setSc3_2() {
        if (consValue[5].equals("N/A"))
            sc3_2 = 0;
        else
            sc3_2 = Integer.parseInt(consValue[5]);
    }

    public int getSc3_2() {
        return sc3_2;
    }

    public void setSc3_3() {
        if (consValue[6].equals("N/A"))
            sc3_3 = 0;
        else
            sc3_3 = Integer.parseInt(consValue[6]);
    }

    public int getSc3_3() {
        return sc3_3;
    }

    public void setSc4() {
        if (consValue[7].equals("N/A"))
            sc4 = 0;
        else
            sc4 = Integer.parseInt(consValue[7]);
    }

    public int getSc4() {
        return sc4;
    }

    public void setSc5_1Max() {
        if (consValue[8].equals("N/A"))
            sc5_1Max = 0;
        else
            sc5_1Max = Integer.parseInt(consValue[8].substring(0,1));
    }

    public int getSc5_1Max() {
        return sc5_1Max;
    }

    public void setSc5_1Min() {
        if (consValue[8].equals("N/A"))
            sc5_1Min = 0;
        else
            sc5_1Min = Integer.parseInt(consValue[8].substring(2,3));
    }

    public int getSc5_1Min() {
        return sc5_1Min;
    }

    public void setSc5_2Max() {
        if (consValue[9].equals("N/A"))
            sc5_2Max = 0;
        else
            sc5_2Max = Integer.parseInt(consValue[9].substring(0,1));
    }

    public int getSc5_2Max() {
        return sc5_2Max;
    }

    public void setSc5_2Min() {
        if (consValue[9].equals("N/A"))
            sc5_2Min = 0;
        else
            sc5_2Min = Integer.parseInt(consValue[8].substring(2,3));
    }

    public int getSc5_2Min() {
        return sc5_2Min;
    }

    public void setSc5_3Max() {
        if (consValue[10].equals("N/A"))
            sc5_3Max = 0;
        else
            sc5_3Max = Integer.parseInt(consValue[10].substring(0,1));
    }

    public int getSc5_3Max() {
        return sc5_3Max;
    }

    public void setSc5_3Min() {
        if (consValue[10].equals("N/A"))
            sc5_3Min = 0;
        else
            sc5_3Min = Integer.parseInt(consValue[10].substring(2,3));
    }

    public int getSc5_3Min() {
        return sc5_3Min;
    }

    public void setSc6(){
        if(consValue[11].equals("N/A"))
            sc6 = false;
        if (consValue[11].equals("Yes"))
            sc6 = true;
    }

    public boolean getSc6() {
        return sc6;
    }

    public void setSc7(){
        if(consValue[12].equals("N/A"))
            sc7 = false;
        if (consValue[12].equals("Yes"))
            sc7 = true;
    }
    public boolean getSc7() {
        return sc7;
    }

    public void setSc8(){
        if(consValue[13].equals("N/A"))
            sc8 = 0;
        else
            sc8 = Integer.parseInt(consValue[13]);
    }

    public int getSc8() {
        return sc8;
    }

    public void setSc9(){
        if(consValue[14].equals("N/A"))
            sc9 = 0;
        else
            sc9 = Integer.parseInt(consValue[14]);
    }

    public int getSc9() {
        return sc9;
    }
}



