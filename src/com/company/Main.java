package com.company;

import org.apache.poi.ss.usermodel.*;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Iterator;
import java.io.*;
import java.util.*;

public class Main {
    public static String filePath;
    public static Manpower[] manpowers;
    public static Employee[] employees;
    public static Shift[] shifts;
    public static Constraint constraints;
    public static int  [][] shiftWday;
    public static int [][] shiftWend;
    public static double [][] hourLimit;
    public static Scanner input;
    public static int selectedProcess;
    public static int selectedFile;

    public static String optimizedPath;



    public static void main(String[] args) throws IOException, ParseException {

        System.out.println("Penjadwalan Perawat Dataset Benchmark Rumah Sakit di Norwegia)");
        System.out.println("1. Solusi Awal");
        System.out.println("2. Optimasi");
        System.out.println("Pilih proses yang diinginkan");
        Scanner input = new Scanner(System.in);
        selectedProcess = input.nextInt();
        for (int i = 0; i < 7;i++){
            System.out.println((i+1)+ ". Optur" + (i+1));
        }
        System.out.println("File terpilih :");
        selectedFile = input.nextInt();

        optimizedPath = "D:\\Kuliah!\\Semester 7\\PTA\\Nurse Rostering Data\\Optimasi\\ OpTur" + (selectedFile) +".txt";
        filePath = "D:\\Kuliah!\\Semester 7\\PTA\\Nurse Rostering Data\\OpTur" + (selectedFile) +".xls";

        
        // Read sheet1 Employee (Int)
        String[][] read = readFile().clone();
        employees = new Employee[read.length];

        String[][] workWeekend = new String[read.length][];
        for (int w = 0; w < read.length; w++)
            workWeekend[w] = read[w][2].split(",");
        int a = 0;
        for (int i = 0; i < workWeekend.length; i++) {
            if (a < workWeekend[i].length)
                a = workWeekend[i].length;
        }

        int[][] weekend = new int[workWeekend.length][a];
        for (int i = 0; i < workWeekend.length; i++) {
            for (int j = 0; j < workWeekend[i].length; j++) {
                weekend[i][j] = Integer.parseInt(workWeekend[i][j]);
            }
        }
        for (int e = 0; e < read.length; e++) {
            employees[e] = new Employee(
                    Integer.parseInt(read[e][0]),
                    Double.parseDouble(read[e][1]),
                    (weekend[e]),
                    (read[e][3]));
        }

//        for (int i = 0; i < employees.length; i++) {
//            System.out.println(
//                    employees[i].getId() + " " +
//                            employees[i].getWorkHour() + " " +
//                            employees[i].getWorkWeekend()[0] + " " +
//                            employees[i].getWorkWeekend()[1] + " " +
//                            employees[i].getCompetence() + " ");
//
//        }

        //Read sheet2 Shift
        String[][] read1 = readFile1().clone();
        shifts = new Shift[read1.length];
        DateFormat time = new SimpleDateFormat("hh mm");
        LocalTime[][] times = new LocalTime[read1.length][2];
        for (int t = 0; t < read1.length; t++) {
            String startTime = read1[t][10] + " " + read1[t][11];
            String endTime = read1[t][12] + " " + read1[t][13];
            Time start = new Time(time.parse(startTime).getTime());
            Time end = new Time(time.parse(endTime).getTime());
            LocalTime startShift = start.toLocalTime();
            LocalTime endShift = end.toLocalTime();
            times[t][0] = startShift;
            times[t][1] = endShift;

        }
//        for (int s = 0; s < read1.length; s++) {
//            shifts[s] = new Shift(
//                    Integer.parseInt(read1[s][0]), Double.parseDouble(),
//                    Double.parseDouble(read1[s][1]), Double.parseDouble(read1[s][2]),
//                    Double.parseDouble(read1[s][3]), Double.parseDouble(read1[s][4]),
//                    Double.parseDouble(read1[s][5]), Double.parseDouble(read1[s][6]),
//                    Double.parseDouble(read1[s][7]), Integer.parseInt(read1[s][8]),
//                    (read1[s][9]), (times[s][0]), (times[s][1]), (read1[s][14]));
//        }

        double[][] duration = new double[readFile1().length][7];
        for (int b = 0; b < duration.length; b++) {
            for (int j = 0; j < duration[b].length; j++) {
                duration[b][j] = Double.parseDouble(readFile1()[b][j + 1]);
            }

//            for (int i = 0; i < shifts.length; i++) {
//            System.out.println(
//                    shifts[i].getIdShift() + " " +
//                            shifts[i].getMon() + " " +
//                            shifts[i].getTue() + " " +
//                            shifts[i].getWed() + " " +
//                            shifts[i].getThur() + " " +
//                            shifts[i].getFri() + " " +
//                            shifts[i].getSat() + " " +
//                            shifts[i].getSun() + " " +
//                            shifts[i].getShiftCat() + " " +
//                            shifts[i].getShiftName() + " " +
//                            shifts[i].getStartTime() +" "+
//                            shifts[i].getEndTime()  + " "+
//                            shifts[i].getCompetence() + " ");
//            }


            //Read sheet3 Manpower
            String[][] read2 = readFile2().clone();
            manpowers = new Manpower[read2.length];
            for (int m = 0; m < read2.length; m++) {
                manpowers[m] = new Manpower(
                        (read2[m][0]),
                        Integer.parseInt(read2[m][1]),
                        Integer.parseInt(read2[m][2]),
                        Integer.parseInt(read2[m][3]),
                        Integer.parseInt(read2[m][4]),
                        Integer.parseInt(read2[m][5]),
                        Integer.parseInt(read2[m][6]),
                        Integer.parseInt(read2[m][7]),
                        Integer.parseInt(read2[m][8]));

            }

            //  for (int i = 0; i < manpowers.length; i++) {
//            System.out.println(
//                    manpowers[i].getManShift() + " " +
//                            manpowers[i].getManMon() + " " +
//                            manpowers[i].getManTue() + " " +
//                            manpowers[i].getManWed() + " " +
//                            manpowers[i].getManThur() + " " +
//                            manpowers[i].getManFri() + " " +
//                            manpowers[i].getManSat() + " " +
//                            manpowers[i].getManSun() + " " +
//                            manpowers[i].getIdMan() + " ");


            // Read sheet4 Constraint
            String[] read3 = readFile3().clone();
            Constraint constraints = new Constraint(read3);
            constraints.setHc1();
            constraints.setHc2();
            constraints.setHc3();
            constraints.setHc4();
            constraints.setHc5_1();
            constraints.setHc5_2();
            constraints.setHc5_3();
            constraints.setHc5_4();
            constraints.setHc5_5();
            constraints.setHc5_6();
            constraints.setHc6();
            constraints.setHc7();

//        System.out.println (Arrays.toString(read3));
//        System.out.println(constraints.getHc1());
//        System.out.println(constraints.getHc2());
//        System.out.println(constraints.getHc3());
//        System.out.println(constraints.getHc4());
//        System.out.println(constraints.getHc5_1());
//        System.out.println(constraints.getHc5_2());
//        System.out.println(constraints.getHc5_3());
//        System.out.println(constraints.getHc5_4());
//        System.out.println(constraints.getHc5_5());
//        System.out.println(constraints.getHc5_6());
//        System.out.println(constraints.getHc6());
//        System.out.println(constraints.getHc7());


// Pasangan Shift yang tidak boleh pada Weekday
            int count = 0;
            for (int i = 0; i < shifts.length; i++)
                for (int j = 0; j < shifts.length; j++) {
                    if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 2)
                        if (constraints.getHc5_1().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0)
                            count++;
                    if (shifts[i].getShiftCat() == 2 && shifts[j].getShiftCat() == 1)
                        if (constraints.getHc5_3().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0)
                            count++;
                    if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 1)
                        // if (constraints.getHc5_5().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0)
                        count++;
                }
            shiftWday = new int[count][2];
            count = 0;
            for (int i = 0; i < shifts.length; i++)
                for (int j = 0; j < shifts.length; j++) {
                    if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 2)
                        if (constraints.getHc5_1().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0) {
                            shiftWday[count][0] = i + 1;
                            shiftWday[count][1] = j + 1;
                            count++;
                        }
                    if (shifts[i].getShiftCat() == 2 && shifts[j].getShiftCat() == 1)
                        if (constraints.getHc5_3().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0) {
                            shiftWday[count][0] = i + 1;
                            shiftWday[count][1] = j + 1;
                            count++;
                        }
                    if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 1) {
                        // if (constraints.getHc5_5().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0) {
                        shiftWday[count][0] = i + 1;
                        shiftWday[count][1] = j + 1;
                        count++;
                    }
                }
// Pasangan Shift yang tidak boleh pada Weekend
            count = 0;
            for (int i = 0; i < shifts.length; i++)
                for (int j = 0; j < shifts.length; j++) {
                    if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 2)
                        if (constraints.getHc5_2().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0)
                            count++;

                    if (shifts[i].getShiftCat() == 2 && shifts[j].getShiftCat() == 1)
                        if (constraints.getHc5_4().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0)
                            count++;

                    if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 1)
                        //  if (constraints.getHc5_6().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0)
                        count++;
                }

            shiftWend = new int[count][2];
            count = 0;
            for (int i = 0; i < shifts.length; i++)
                for (int j = 0; j < shifts.length; j++) {
                    if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 2)
                        if (constraints.getHc5_2().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0) {
                            shiftWend[count][0] = i + 1;
                            shiftWend[count][1] = j + 1;
                            count++;
                        }

                    if (shifts[i].getShiftCat() == 2 && shifts[j].getShiftCat() == 1)
                        if (constraints.getHc5_4().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0) {
                            shiftWend[count][0] = i + 1;
                            shiftWend[count][1] = j + 1;
                            count++;
                        }

                    if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 1) {
                        //  if (constraints.getHc5_6().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0) {
                        shiftWend[count][0] = i + 1;
                        shiftWend[count][1] = j + 1;
                        count++;
                    }

                }
        }
//        System.out.println("Pasangan shift tidak boleh Weekend");
//        for (int i = 0; i < shiftWend.length; i++) {
//            System.out.println(Arrays.toString(shiftWend[i]));
//        }
//
//        System.out.println("Pasangan shift tidak boleh Weekdays");
//        for (int i = 0; i < shiftWday.length; i++) {
//            System.out.println(Arrays.toString(shiftWday[i]));
//
//        }
        double[][] hourLimit = new double[employees.length][3];
        for (int i = 0; i < hourLimit.length; i++) {
            hourLimit [i][0] = employees[i].getWorkHour() - (employees[i].getWorkHour()*constraints.getHc3());
            hourLimit [i][1] = employees[i].getWorkHour();
            hourLimit [i][2] = employees[i].getWorkHour() + (employees[i].getWorkHour()*constraints.getHc3());
        }





           //initsol();
        public static void initialSolution() {
            int [][] matrix_solution = new int[employees.length][42];
            for(int i = 0; i<matrix_solution.length; i++)
                for(int j=0; j<matrix_solution[i].length;  j++)
                    matrix_solution[i][j] = 0;

            for (int i = 5; i < 42; i=i+7) {
                while (!validDay(matrix_solution, i)) {
                    int shift = isMissing(matrix_solution, i);
                    int randomEmp = (int) (Math.random() * employees.length);
                    if (checkHC2(matrix_solution, i, shift, randomEmp))
                        matrix_solution[randomEmp][i] = shift;
                }
            }
            for (int i = 6; i < 42; i=i+7) {
                while (!validDay(matrix_solution, i)) {
                    int shift = isMissing(matrix_solution, i);
                    int randomEmp = (int) (Math.random() * employees.length);
                    if (checkHC2(matrix_solution, i, shift, randomEmp))
                        matrix_solution[randomEmp][i] = shift;
                }
            }
            for (int i = 0; i < 42; i++) {
                if (i % 7 == 5 || i % 7 == 6)
                    continue;
                while (!validDay(matrix_solution, i)) {
                    int shift = isMissing(matrix_solution, i);
                    int randomEmp = (int) (Math.random() * employees.length);
                    if (checkHC2(matrix_solution, i, shift, randomEmp))
                        matrix_solution[randomEmp][i] = shift;
                }
            }
        }
        }

    private static int isMissing(int[][] matrix_solution, int i) {
        for (int i = 0; i < plan.length; i++)
            if (plan[i].getNeed(day % 7) > needs(solution, i+1, day))
                return i+1;
        return 0;
    }

    private static boolean validDay(int[][] matrix_solution, int i) {
    }
    


    public static double [] allWorkHour (int [][] solution, int week){
        double [] workhour_week = new double [15];
        for (int i = 0; i < 15; i++) {
            for (int j = week*7; j < (week+1)*7; j++) {
                if (solution [i][j] != 0) {
                    workhour_week[i] = workhour_week[i] + shifts[solution[i][j]-1].getDuration(j % 7);
                }
            }
        }
        return workhour_week;
    }
    public static double [] averageWorkHour (int [][] solution, int week){
        double [] avg = new double[15];

        for (int i = 0; i < week; i++) {
            for (int j = 0; j < 15; j++) {
                avg [j] = avg[j] + allWorkHour(solution, i)[j];
            }
        }
        for (int i = 0; i < 15; i++) {
            avg [i] = (avg[i]/week);
        }
        return avg;
    }






    // Membuat Matriks
    public static void initsol() {
        int[][] soltmatrix = new int[15][42];

        // Assign Jadwal Weekend (5-6)
        for (int i = 0; i < 42; i++) {
            if (i % 7 == 5 || i % 7 == 6) {
                for (int j = 0; j < 15; j++) {
                    for (int a = 0; a < 6; a++) {
                        //Mengecek HC
                        if (checkHC2(soltmatrix, i, a, j)) {
                            if (checkHC4Com(a, j)) {
                                if (checkHC4Weekend(i, j)) {
                                    if (checkHC7(soltmatrix, i, a, j)) {
                                        if (checkHC5(soltmatrix, i, a+1, j)) {
                                            soltmatrix[j][i] = a+1;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
//         Assign Jadwal Weekdays (0-4)
        for (int i = 0; i < 42; i++) {
            if (i % 7 == 0 || i % 7 == 1 || i % 7 == 2 || i % 7 == 3 || i % 7 == 4) {
                for (int j = 0; j < 15; j++) {
                    for (int a = 0; a < 6; a++) {
                        // Mengecek HC
                        if (checkHC2(soltmatrix, i, a, j)) {
                            if (checkHC4Com(a, j)) {
                                if (checkHC4Weekend(i, j)) {
                                    if (checkHC7(soltmatrix, i, a, j)) {
                                        if (checkHC5(soltmatrix, i, a+1, j)) {
                                            soltmatrix[j][i] = a + 1;
                                            break;
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        for (int i = 0; i < soltmatrix.length; i++) {
            for (int j = 0; j < soltmatrix[i].length; j++) {
                System.out.print(soltmatrix[i][j] + " ");
            }
            System.out.println("");
        }
    }


    public static int needs(int[][] solution, int shift, int day) {
        int count = 0;
        for (int i = 0; i < solution.length; i++) {
            if (solution[i][day] == shift + 1)
                count++;
        }
        return count;
    }

    public static boolean checkHC2(int[][] solution, int day, int shift, int employee) {
        if (day % 7 == 0)
            if (needs(solution, shift, day) < manpowers[shift].getManMon())
                return true;
        if (day % 7 == 1)
            if (needs(solution, shift, day) < manpowers[shift].getManTue())
                return true;
        if (day % 7 == 2)
            if (needs(solution, shift, day) < manpowers[shift].getManWed())
                return true;
        if (day % 7 == 3)
            if (needs(solution, shift, day) < manpowers[shift].getManThur())
                return true;
        if (day % 7 == 4)
            if (needs(solution, shift, day) < manpowers[shift].getManFri())
                return true;
        if (day % 7 == 5)
            if (needs(solution, shift, day) < manpowers[shift].getManSat())
                return true;
        if (day % 7 == 6)
            if (needs(solution, shift, day) < manpowers[shift].getManSun())
                return true;
        return false;
    }
    public static boolean checkHC3 (int [][] solution){
        double [] hourWork = averageWorkHour(solution, 42);
        for (int i = 0; i < hourWork.length; i++) {
            if (hourWork[i] > hourLimit[i][2])
                return false;
            if (hourWork[i] < hourLimit[i][0])
                return false;
        }
        return true;
    }

    public static boolean checkHC4Com(int shift, int employee) {
        if (shifts[shift].getCompetence().equals("A") && employees[employee].getCompetence().equals("")) {
            return false;
        }
        return true;
    }

    public static boolean checkHC4Weekend(int day, int employee) {
        if (day % 7 == 5 || day % 7 == 6) {
            for (int i = 0; i < employees[employee].getWorkWeekend().length; i++) {
                if (day / 7 == employees[employee].getWorkWeekend()[i] - 1)
                    return true;
            }
            return false;
        }
        return true;
    }

    public static boolean checkHC5 (int [][] solution, int day, int shift, int employee) {
        if (day == 0)
            return true;

        if (day % 7 == 5 || day % 7 == 6) {
            for (int i = 0; i < shiftWend.length; i++){
                if (solution[employee][day-1] == shiftWend[i][0] && shift == shiftWend [i][1]){
                    return false;
                }
                if (day < 41) {
                    if (solution [employee][day+1]== shiftWend[i][1] && shift == shiftWend[i][0])
                        return false;
                }
            }

        }
        if (day % 7 == 0 || day % 7 == 1 || day % 7 == 2 || day % 7 == 3 || day % 7 == 4){
            for (int i = 0; i < shiftWday.length; i++){
                if (solution[employee][day-1] == shiftWday[i][0] && shift == shiftWday [i][1]){
                    return false;
                }
                if (day < 41) {
                    if (solution [employee][day+1]== shiftWday[i][1] && shift == shiftWday[i][0])
                        return false;
                }
            }

        }

        return true;
    }
    public static boolean[] checkFreeWeekend(int[][] solution, int week) {
        boolean[] isOk = new boolean[employees.length];
        for (int i = 0; i < isOk.length; i++)
            isOk[i] = false;
        label : for(int j=0;  j<employees.length; j++) {
            for(int i=7*week; i<(week+1)*7;i++)
            {
                if(solution[j][i]==0)
                {
                    if(i==week*7)
                    {
                        if(solution[j][i+1]==0)
                        {
                            isOk[j] = true;
                            continue label;
                        }
                        else
                        {
                            if(shifts[solution[j][i+1]-1].getShiftCat()!=1)
                            {
                                isOk[j] = true;
                                continue label;
                            }
                            else
                            {
                                LocalTime lessthan =  LocalTime.parse("08:00");
                                if(shifts[solution[j][i+1]-1].getStartTime().isAfter(lessthan));
                                {
                                    isOk[j] = true;
                                    continue label;
                                }
                            }
                        }
                    }
                    if(i>week*7 && i<((week+1)*7)-1)
                    {
                        if(solution[j][i+1]==0||solution[j][i-1]==0)
                        {
                            isOk[j] = true;
                            continue label;
                        }
                        if(solution[j][i+1]!=0&&shifts[solution[j][i+1]-1].getShiftCat()!=1)
                        {
                            isOk[j] = true;
                            continue label;
                        }
                        if(solution[j][i+1]!=0&&shifts[solution[j][i+1]-1].getShiftCat()==1)
                        {
                            if(shifts[solution[j][i-1]-1].getShiftCat()!=3)
                            {
                                LocalTime lessthan  = LocalTime.parse("08:00");
                                LocalTime limit = LocalTime.parse("00:00");
                                LocalTime before = limit.minusHours(shifts[solution[j][i-1]-1].getEndTime().getHour()).minusMinutes(shifts[solution[j][i-1]-1].getEndTime().getMinute());
                                LocalTime total = before.plusHours(shifts[solution[j][i+1]-1].getStartTime().getHour()).minusMinutes(shifts[solution[j][i+1]-1].getStartTime().getMinute());
                                if(total.isAfter(lessthan))
                                {
                                    isOk[j] = true;
                                    continue label;
                                }
                            }
                        }
                    }
                    if(i==((week+1)*7)-1)
                    {
                        if(solution[j][i-1]==0)
                        {
                            isOk[j] = true;
                            continue label;
                        }
                        else
                        {
                            LocalTime  lessthan  = LocalTime.parse("08:00");
                            LocalTime limit = LocalTime.parse("00:00");
                            LocalTime before = limit.minusHours(shifts[solution[j][i-1]-1].getEndTime().getHour()).minusMinutes(shifts[solution[j][i-1]-1].getEndTime().getMinute());
                            if(before.isAfter(lessthan))
                            {
                                isOk[j] = true;
                                continue label;
                            }
                        }
                    }
                }
            }
        }
        return isOk;
    }


    public static boolean checkHC6 (int [][] solution) {
        boolean [][] check = new boolean[employees.length][42]];
        for (int i = 0; i < 42; i++) {
            boolean [] check2 = checkFreeWeekend(solution, i).clone(); {
                for (int j = 0; j < check2.length; j++) {
                    check[j][i] = check2[j];
                }
            }
        }

        for (int i = 0; i < check.length; i++) {
            for (int j = 0; j < check[i].length; j++) {
                if (!check[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    public static boolean checkHC7(int[][] solution, int day, int shift, int employee) {
        double sumTotal = 0;
        int week = day / 7;
        for (int i = week * 7; i < (week + 1) * 7; i++) {
            if (solution[employee][i] != 0) {
                if (i % 7 == 0) {
                    sumTotal += shifts[solution[employee][i] - 1].getMon();
                }
                if (i % 7 == 1) {
                    sumTotal += shifts[solution[employee][i] - 1].getTue();
                }
                if (i % 7 == 2) {
                    sumTotal += shifts[solution[employee][i] - 1].getWed();
                }
                if (i % 7 == 3) {
                    sumTotal += shifts[solution[employee][i] - 1].getThur();
                }
                if (i % 7 == 4) {
                    sumTotal += shifts[solution[employee][i] - 1].getFri();
                }
                if (i % 7 == 5) {
                    sumTotal += shifts[solution[employee][i] - 1].getSat();
                }
                if (i % 7 == 6) {
                    sumTotal += shifts[solution[employee][i]].getSun();
                }
            }
            if (sumTotal + shifts[shift].getMon() <= 48)
                return true;
            if (sumTotal + shifts[shift].getTue() <= 48)
                return true;
            if (sumTotal + shifts[shift].getWed() <= 48)
                return true;
            if (sumTotal + shifts[shift].getThur() <= 48)
                return true;
            if (sumTotal + shifts[shift].getFri() <= 48)
                return true;
            if (sumTotal + shifts[shift].getSat() <= 48)
                return true;
            if (sumTotal + shifts[shift].getSun() <= 48)
                return true;
        }

        return false;
    }


    @NotNull
    public static String[][] readFile() throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(file));
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormat = new DataFormatter();

        Row row;
        int mendatar = 0;
        int menurun = 0;
        int mendatarfix = 0;
        for (int i = 5; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormat.formatCellValue(cell);
                // System.out.print(cellValue + "\t");
                mendatar++;
            }
            if (mendatarfix < mendatar)
                mendatarfix = mendatar;
            mendatar = 0;
            menurun++;
            //  System.out.println();
        }

        String[][] iniarray = new String[menurun][100];
        menurun = 0;
        for (int i = 5; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {

                String cellValue = dataFormat.formatCellValue(cell);
                iniarray[menurun][mendatar] = cellValue;
                mendatar++;
            }
            mendatar = 0;
            menurun++;
        }
        return iniarray;
    }

    @NotNull

    public static String[][] readFile1() throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(file));
        Sheet sheet = workbook.getSheetAt(1);
        DataFormatter dataFormat = new DataFormatter();


        Row row;
        int mendatar = 0;
        int menurun = 0;
        int mendatarfix = 0;
        for (int i = 4; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
//                String cellValue = dataFormat.formatCellValue(cell);
//                // System.out.print(cellValue + "\t");
                mendatar++;
            }
            if (mendatarfix < mendatar)
                mendatarfix = mendatar;
            mendatar = 0;
            menurun++;
            // System.out.println();
        }

        String[][] iniarray = new String[menurun][100];
        menurun = 0;
        for (int i = 4; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {

                String cellValue = dataFormat.formatCellValue(cell);
                iniarray[menurun][mendatar] = cellValue;
                mendatar++;
            }
            mendatar = 0;
            menurun++;
        }
        return iniarray;
    }

    @NotNull
    public static String[][] readFile2() throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(file));
        Sheet sheet = workbook.getSheetAt(2);
        DataFormatter dataFormat = new DataFormatter();

        Row row;
        int mendatar = 0;
        int menurun = 0;
        int mendatarfix = 0;
        for (int i = 5; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormat.formatCellValue(cell);
                // System.out.print(cellValue + "\t");
                mendatar++;
            }
            if (mendatarfix < mendatar)
                mendatarfix = mendatar;
            mendatar = 0;
            menurun++;
            // System.out.println();
        }

        String[][] iniarray = new String[menurun][100];
        menurun = 0;
        for (int i = 5; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {

                String cellValue = dataFormat.formatCellValue(cell);
                iniarray[menurun][mendatar] = cellValue;
                mendatar++;
            }
            mendatar = 0;
            menurun++;
        }

        return iniarray;
    }

    public static String [] readFile3() throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(file));
        Sheet sheet = workbook.getSheetAt(3);
        DataFormatter dataFormat = new DataFormatter();

        Row row;
        int mendatar = 0;
        int menurun = 0;
        int mendatarfix = 0;
        for (int i = 4; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormat.formatCellValue(cell);
                // System.out.print(cellValue + "\t");
                mendatar++;
            }
            if (mendatarfix < mendatar)
                mendatarfix = mendatar;
            mendatar = 0;
            menurun++;
        }
        String [] iniarray = new String[menurun];
        menurun = 0;
        for (int i = 4; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {
                String cellValue = dataFormat.formatCellValue(cell);
                iniarray[menurun] = cellValue;
                mendatar++;
            }
            mendatar = 0;
            menurun++;
        }
//        System.out.print(iniarray.length);
//        System.out.println(mendatarfix);
        return iniarray;
    }

}


