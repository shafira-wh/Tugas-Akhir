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
    public static int[][] shiftWday;
    public static int[][] shiftWend;
    public static double [][] hourLimit;
    public static Scanner input;
    public static String[][] wantedPattern;
    public static Pattern [] thePattern;
       public static String[][] unwantedPattern;
    public static Pattern [] notPattern;
    public static int selectedProcess;
    public static int selectedFile;
    public static int [] plannedHorizon;

    public static String optimizedPath;


    public static void main(String[] args) throws IOException, ParseException {

        System.out.println("Penjadwalan Perawat Dataset Benchmark Rumah Sakit di Norwegia)");
        System.out.println("1. Solusi Awal");
        System.out.println("2. Optimasi");
        System.out.println("Pilih proses yang diinginkan");
        Scanner input = new Scanner(System.in);
        selectedProcess = input.nextInt();

        for (int i = 0; i < 7; i++) {
            System.out.println((i + 1) + ". Optur" + (i + 1));
        }

        System.out.println("File terpilih :");
        selectedFile = input.nextInt();

        optimizedPath = "D:\\Kuliah!\\Semester 7\\PTA\\Nurse Rostering Data\\Optimasi\\ OpTur" + (selectedFile) + ".txt";
        filePath = "D:\\Kuliah!\\Semester 7\\PTA\\Nurse Rostering Data\\OpTur" + (selectedFile) + ".xls";

        String[][] readEmployee = sheetEmployee().clone();
        String[][] readShift = sheetShift().clone();
        String[][] readManpower = sheetManpower().clone();
        String[] readConstraint = sheetConstraint().clone();
        wantedPattern = sheetWanted().clone();
        unwantedPattern = sheetUnwanted().clone();


        // Objek Employee

        employees = new Employee[readEmployee.length];
        String[][] workWeekend = new String[readEmployee.length][];
        for (int w = 0; w < readEmployee.length; w++)
            workWeekend[w] = readEmployee[w][2].split(",");
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
        for (int e = 0; e < readEmployee.length; e++) {
            employees[e] = new Employee(
                    Integer.parseInt(readEmployee[e][0]),
                    Double.parseDouble(readEmployee[e][1]),
                    (weekend[e]),
                    (readEmployee[e][3]));
        }

//        for (int i = 0; i < readEmployee.length; i++) {
//            System.out.println(
//                    employees[i].getId() + " " +
//                            employees[i].getWorkHour() + " " +
//                            employees[i].getWorkWeekend()[0] + " " +
//                            employees[i].getWorkWeekend()[1] + " " +
//                            employees[i].getCompetence() + " ");
//
//        }

        //Objek Shift
        shifts = new Shift[readShift.length];
        DateFormat time = new SimpleDateFormat("hh mm");
        LocalTime[][] times = new LocalTime[readShift.length][2];
        for (int t = 0; t < readShift.length; t++) {
            String startTime = readShift[t][10] + " " + readShift[t][11];
            String endTime = readShift[t][12] + " " + readShift[t][13];
            Time start = new Time(time.parse(startTime).getTime());
            Time end = new Time(time.parse(endTime).getTime());
            LocalTime startShift = start.toLocalTime();
            LocalTime endShift = end.toLocalTime();
            times[t][0] = startShift;
            times[t][1] = endShift;

        }

        double[][] duration = new double[readShift.length][7];
        for (int b = 0; b < duration.length; b++) {
            for (int j = 0; j < duration[b].length; j++) {
                duration[b][j] = Double.parseDouble(readShift[b][j + 1]);
            }
        }
        for (int s = 0; s < readShift.length; s++) {
            shifts[s] = new Shift(
                    Integer.parseInt(readShift[s][0]),
                    (duration[s]),
                    Integer.parseInt(readShift[s][8]),
                    (readShift[s][9]),
                    (times[s][0]), (times[s][1]),
                    (readShift[s][14]));
        }


        //Objek Manpower
        manpowers = new Manpower[readManpower.length];

        int[][] neededEmp = new int[readManpower.length][7];
        for (int i = 0; i < neededEmp.length; i++) {
            for (int j = 0; j < neededEmp[i].length; j++) {
                neededEmp[i][j] = Integer.parseInt(readManpower[i][j + 1]);
            }
        }

        for (int m = 0; m < readManpower.length; m++) {
            manpowers[m] = new Manpower(
                    (readManpower[m][0]),
                    (neededEmp[m]),
                    Integer.parseInt(readManpower[m][8]));
        }


        // Read sheet4 Constraint
        constraints = new Constraint(readConstraint);
        //Hard Constraint
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
        //Soft Constraint
        constraints.setSc1_1();
        constraints.setSc1_2();
        constraints.setSc1_3();
        constraints.setSc2();
        constraints.setSc3_1();
        constraints.setSc3_2();
        constraints.setSc3_3();
        constraints.setSc4();
        constraints.setSc5_1Min();
        constraints.setSc5_1Max();
        constraints.setSc5_2Min();
        constraints.setSc5_2Max();
        constraints.setSc5_3Min();
        constraints.setSc5_3Max();
        constraints.setSc6();
        constraints.setSc7();
        constraints.setSc8();
        constraints.setSc9();

        //OBJEK WANTED PATTERN
        if (constraints.getSc8() != 0) {
            int[] wantedDay = new int[constraints.getSc8()];
            int index = 0;
            for (int i = 0; i < wantedPattern.length; i++) {
                if (wantedPattern[i][1].equals("Monday")) {
                    wantedDay[index] = 0;
                    index++;
                }
                if (wantedPattern[i][1].equals("Tuesday")) {
                    wantedDay[index] = 1;
                    index++;
                }
                if (wantedPattern[i][1].equals("Wednesday")) {
                    wantedDay[index] = 2;
                    index++;
                }
                if (wantedPattern[i][1].equals("Thursday")) {
                    wantedDay[index] = 3;
                    index++;
                }
                if (wantedPattern[i][1].equals("Friday")) {
                    wantedDay[index] = 4;
                    index++;
                }
                if (wantedPattern[i][1].equals("Saturday")) {
                    wantedDay[index] = 5;
                    index++;
                }
                if (wantedPattern[i][1].equals("Sunday")) {
                    wantedDay[index] = 6;
                    index++;
                }
            }
            String[][] pattern = new String[constraints.getSc8()][];
            index = 0;
            for (int i = 0; i < wantedPattern.length; i++) {
                pattern[index] = wantedPattern[i][2].split(",");
            }
            thePattern = new Pattern[constraints.getSc8()];
            for (int i = 0; i < constraints.getSc8(); i++) {
                thePattern[i] = new Pattern(wantedDay[i], pattern[i]);
            }
        }

        //OBJEK UNWANTED PATTERN
        if (constraints.getSc9() != 0) {
            int[] wantedDay = new int[constraints.getSc9()];
            int index = 0;
            for (int i = 0; i < wantedPattern.length; i++) {
                if (wantedPattern[i][1].equals("Monday")) {
                    wantedDay[index] = 0;
                    index++;
                }
                if (wantedPattern[i][1].equals("Tuesday")) {
                    wantedDay[index] = 1;
                    index++;
                }
                if (wantedPattern[i][1].equals("Wednesday")) {
                    wantedDay[index] = 2;
                    index++;
                }
                if (wantedPattern[i][1].equals("Thursday")) {
                    wantedDay[index] = 3;
                    index++;
                }
                if (wantedPattern[i][1].equals("Friday")) {
                    wantedDay[index] = 4;
                    index++;
                }
                if (wantedPattern[i][1].equals("Saturday")) {
                    wantedDay[index] = 5;
                    index++;
                }
                if (wantedPattern[i][1].equals("Sunday")) {
                    wantedDay[index] = 6;
                    index++;
                }
            }
            String[][] pattern = new String[constraints.getSc9()][];
            index = 0;
            for (int i = 0; i < unwantedPattern.length; i++) {
                pattern[index] = unwantedPattern[i][2].split(",");
            }
            notPattern = new Pattern[constraints.getSc9()];
            for (int i = 0; i < constraints.getSc9(); i++) {
                notPattern[i] = new Pattern(wantedDay[i], pattern[i]);
                System.out.println(Arrays.toString(notPattern[i].getShiftPattern()));
            }
        }

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


        hourLimit = new double[employees.length][7];
        for (int i = 0; i < hourLimit.length; i++) {
            hourLimit[i][0] = employees[i].getWorkHour() - (employees[i].getWorkHour() * constraints.getHc3());
            hourLimit[i][1] = employees[i].getWorkHour();
            hourLimit[i][2] = employees[i].getWorkHour() + (employees[i].getWorkHour() * constraints.getHc3());
        }
        if (selectedProcess==1)
            initialSolution();
        if (selectedProcess==2);
            optimizedSolution();

    }

           //initsol();
        public static void initialSolution() {
            int [][] matrix_solution = new int[employees.length][plannedHorizon[selectedFile-1]*7];
            for(int i = 0; i<matrix_solution.length; i++)
                for(int j=0; j<matrix_solution[i].length;  j++)
                    matrix_solution[i][j] = 0;

            for (int i = 5; i < plannedHorizon[selectedFile-1]*7; i=i+7) {
                while (!validDay(matrix_solution, i)) {
                    int shift = isMissing(matrix_solution, i);
                    int randomEmp = (int) (Math.random() * employees.length);
                    if (checkHC2(matrix_solution, i, shift, randomEmp))
                        matrix_solution[randomEmp][i] = shift;
                }
            }
            for (int i = 6; i < plannedHorizon[selectedFile-1]*7; i=i+7) {
                while (!validDay(matrix_solution, i)) {
                    int shift = isMissing(matrix_solution, i);
                    int randomEmp = (int) (Math.random() * employees.length);
                    if (checkHC2(matrix_solution, i, shift, randomEmp))
                        matrix_solution[randomEmp][i] = shift;
                }
            }
            for (int i = 0; i < plannedHorizon[selectedFile-1]*7; i++) {
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

 public static void optimizedSolution(){

}

    public static void print(int[][] printAll)
    {
        for(int i = 0; i<printAll.length; i++)
        {
            for(int j=0; j<printAll[i].length; j++)
            {
                System.out.print(printAll[i][j] + " ");
            }
            System.out.println();
        }
    }
    private static int isMissing(int[][] solution, int day) {
        for (int i = 0; i < manpowers.length; i++)
            if (manpowers[i].getShiftNeeded(day % 7) > needs(solution, i+1, day))
                return i+1;
        return 0;
    }

    private static boolean validDay(int [][] solution, int day) {
        for (int i =0; i<manpowers.length; i++) {
            if (manpowers[i].getShiftNeeded(day % 7) != needs(solution, i + 1, day))
                return false;

        }
        return true;
        }

        public static int checkAllHc(int [][] solution){
        if (!checkHC2(solution))
            return 2;
            if (!checkHC3(solution))
                return 3;
            if (!checkHc4Com(solution))
                return 4;
            if (!checkHc5(solution))
                return 5;
            if (!checkHC6(solution))
                return 6;
            if (!checkHc7(solution))
                return 7;
            return false;

    }

    public static void exchangeTwo (int [][] solution) {
        int randomDay =  -1;
        do{
            randomDay = (int) (Math.random() * plannedHorizon[selectedFile-1]*7);
        } while (randomDay % 7 == 5 || randomDay % 7 == 6);

        int randomEmp1 = -1;
        int randomEmp2 = -1;
//        int randomEmp1 = (int) (Math.random() * emp.length);
//        int randomEmp2 = (int) (Math.random() * emp.length);
        do {
            randomEmp1 = (int) (Math.random() * employees.length);
        } while (solution[randomEmp1][randomDay]==0);

        do {
            randomEmp2 = (int) (Math.random() * employees.length);
        } while (solution[randomEmp2][randomDay]!=0);

        int shiftExchange = solution[randomEmp1][randomDay];
        solution[randomEmp1][randomDay] = solution[randomEmp2][randomDay];
        solution[randomEmp2][randomDay] = shiftExchange;
    }
    public static void exchangeThree (int [][] solution) {
        int randomDay = (int) (Math.random() * plannedHorizon[selectedFile-1]*7);
        int randomEmp1 = -1;
        int randomEmp2 = -1;
        int randomEmp3 = -1;

        do {
            randomEmp1 = (int) (Math.random() * employees.length);
        } while (solution[randomEmp1][randomDay] == 0 || randomEmp1 == randomEmp2 || randomEmp1 == randomEmp3);
        do {
            randomEmp2 = (int) (Math.random() * employees.length);
        } while (solution[randomEmp2][randomDay] == 0 || randomEmp2 == randomEmp1 || randomEmp2 == randomEmp3);
        do {
            randomEmp3 = (int) (Math.random() * employees.length);
        } while (solution[randomEmp3][randomDay] == 0 || randomEmp3 == randomEmp1 || randomEmp3 == randomEmp2);

        int shiftExchange = solution[randomEmp1][randomDay];
        solution[randomEmp1][randomDay] = solution[randomEmp2][randomDay];
        solution[randomEmp2][randomDay] = solution[randomEmp3][randomDay];
        solution[randomEmp3][randomDay] = shiftExchange;
    }
    public static void double2Exchange(int [][] solution) {
        int randomEmp1 = -1;
        int randomEmp2 = -1;
        int randomDay1 = -1;
        int randomDay2 = -1;
        while (randomEmp1 == -1 || randomEmp2 == -1) {
            do {
                randomDay1 = (int) (Math.random() * plannedHorizon[selectedFile-1]*7);
                randomDay2 = (int) (Math.random() * plannedHorizon[selectedFile-1]*7);
            } while (randomDay1 % 7 == 5 || randomDay1 % 7 == 6 || randomDay2 % 7 == 5 || randomDay2 % 7 == 6);
            for (int i = 0; i < employees.length; i++) {
                for (int j = 0; j < employees.length; j++) {
                    if (solution[i][randomDay1] != 0 && solution[j][randomDay2] != 0 && shifts[solution[i][randomDay1]-1].getShiftCat() == shifts[solution[j][randomDay2]-1].getShiftCat())
                        if (solution[i][randomDay2] == 0 && solution[j][randomDay1] == 0) {
                            randomEmp1 = i;
                            randomEmp2 = j;
                        }
                }
            }
        }
        solution[randomEmp2][randomDay1] = solution[randomEmp1][randomDay1];
        solution[randomEmp1][randomDay1] = 0;
        solution[randomEmp1][randomDay2] = solution[randomEmp2][randomDay2];
        solution[randomEmp2][randomDay2] = 0;
    }

    public static int plannedHorizon (int [][] weeklength){
        if ((weeklength.length == 0|| (weeklength[0].length == 0))) {
            System.out.println("Empty Array");
        }
        int max = weeklength[0][0];
        for (int i=0;i<weeklength.length;i++){
            for (int j=0;j<weeklength[i].length;i++){
            if (weeklength[i][j] > max){
                max = weeklength[i][j];
            }
        }
    }
        return max;
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

    public static boolean AssignHC2(int[][] solution, int day, int shift, int employee) {
        if (manpowers[shift - 1].getShiftNeeded(day % 7) > needs(solution, shift, day))
            if (AssignHC4Com(shift, employee)) {
                if (AssignHC4Weekend(day, employee)) {
                    if (AssignHC7(solution, day, shift, employee)) {
                        if (AssignHC5(solution, day, shift, employee)) {
                            return true;
                        }
                    }
                }
            }
        return false;
    }



    public static boolean AssignHC4Com(int shift, int employee) {
        if (shifts[shift-1].getCompetence().equals("A") && employees[employee].getCompetence().equals("")) {
            return false;
        }
        return true;
    }

    public static boolean AssignHC4Weekend(int day, int employee) {
        if (day % 7 == 5 || day % 7 == 6) {
            for (int i = 0; i < employees[employee].getWorkWeekend().length; i++) {
                if (day / 7 == employees[employee].getWorkWeekend()[i] - 1)
                    return true;
            }
            return false;
        }
        return true;
    }

    public static boolean AssignHC5 (int [][] solution, int day, int shift, int employee) {
        if (day == 0)
            return true;

        if (day % 7 == 5 || day % 7 == 6) {
            for (int i = 0; i < shiftWend.length; i++){
                if (solution[employee][day-1] == shiftWend[i][0] && shift == shiftWend [i][1]){
                    return false;
                }
                if (day < (plannedHorizon[selectedFile-1]*7)-1) {
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
                if (day < (plannedHorizon[selectedFile-1]*7)-1); {
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
        boolean [][] check = new boolean[employees.length][plannedHorizon[selectedFile-1]];
        for (int i = 0; i < plannedHorizon[selectedFile-1]; i++) {
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


    public static boolean AssignHC7(int[][] solution, int day, int shift, int employee) {
        double sumTotal = shifts[shift - 1].getDuration(day % 7);
        if (sumTotals(solution, day, employee) + sumTotal <= constraints.getHc7()) {
        return true;
    }
        return false;
    }
    public static double sumTotals (int [][] solution, int day, int employee) {
        double count = 0;
        int week = day / 7;
        for (int i = week * 7; i < (week + 1); i++) {
            if (solution[employee][i] != 0) {
                count = count + shifts[solution[employee][i] - 1].getDuration(day % 7);
            }
        }
        return count;
    }
    public static boolean checkHC2(int [][] solution ){
        for (int i=0; i<plannedHorizon[selectedFile-1]*7; i++){
            for (int j=0; j<manpowers.length; j++){
                if (manpowers[j].getShiftNeeded(i%7)!= needs(solution, j+1, i))
                    return false;
            }
        }
    return true;
    }
    public static boolean checkHC3 (int [][] solution){
        double [] hourWork = averageWorkHour(solution, plannedHorizon[selectedFile-1]).clone();
        for (int i = 0; i < hourWork.length; i++) {
            if (hourWork[i] > hourLimit[i][2])
                return false;
            if (hourWork[i] < hourLimit[i][0])
                return false;
        }
        return true;
    }
    public static boolean checkHC4Com (int [][] solution) {
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                if (solution[i][j] != 0) {
                    if (shifts[solution[i][j] - 1].getCompetence().equals("A") && employees[i].getCompetence().equals("")) {
                        return false;
                    }
                }
            }
        }
    }


    @NotNull
    public static String[][] sheetEmployee() throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(filePath));
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

        String[][] dataEmployee = new String[menurun][mendatarfix];
        menurun = 0;
        for (int i = 5; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {

                String cellValue = dataFormat.formatCellValue(cell);
                dataEmployee[menurun][mendatar] = cellValue;
                mendatar++;
            }
            mendatar = 0;
            menurun++;
        }
        return dataEmployee;
    }

    @NotNull

    public static String[][] sheetShift () throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(filePath));
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

        String[][] dataShift = new String[menurun][mendatarfix];
        menurun = 0;
        for (int i = 4; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {

                String cellValue = dataFormat.formatCellValue(cell);
                dataShift[menurun][mendatar] = cellValue;
                mendatar++;
            }
            mendatar = 0;
            menurun++;
        }
        return dataShift;
    }

    @NotNull
    public static String[][] sheetManpower() throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(filePath));
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

        String[][] dataManpower = new String[menurun][mendatarfix];
        menurun = 0;
        for (int i = 5; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {

                String cellValue = dataFormat.formatCellValue(cell);
                dataManpower[menurun][mendatar] = cellValue;
                mendatar++;
            }
            mendatar = 0;
            menurun++;
        }

        return dataManpower;
    }

    public static String [] sheetConstraint() throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(filePath));
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
        String [] dataConstraint = new String[menurun];
        menurun = 0;
        for (int i = 4; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {
                String cellValue = dataFormat.formatCellValue(cell);
                dataConstraint[menurun] = cellValue;
                mendatar++;
            }
            mendatar = 0;
            menurun++;
        }
//        System.out.print(iniarray.length);
//        System.out.println(mendatarfix);
        return dataConstraint;
    }

    public static String [][] sheetWanted() throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(filePath));
        Sheet sheet = workbook.getSheetAt(4);
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
        String [][] dataWanted = new String[menurun][mendatarfix];
        menurun = 0;
        for (int i = 4; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {
                String cellValue = dataFormat.formatCellValue(cell);
                dataWanted[menurun][mendatarfix] = cellValue;
                mendatar++;
            }
            mendatar = 0;
            menurun++;
        }
        int count = 0;
        for (int i = 0; i<dataWanted.length; i++){
            if (dataWanted[i][0].isEmpty())
                break;
            count++;
        }
        String [][] wanted = new String[count][3];
        for (int i = 0; i <wanted.length;i++){
            for (int j = 0;j<wanted[i].length; j++){
                wanted[i][j] = dataWanted[i][j];
            }
        }
        return wanted;
    }
    public static String [][] sheetUnwanted() throws IOException {
        Workbook workbook = WorkbookFactory.create(new File(filePath));
        Sheet sheet = workbook.getSheetAt(4);
        DataFormatter dataFormat = new DataFormatter();

        Row row;
        int mendatar = 0;
        int menurun = 0;
        int mendatarfix = 0;
        for (int i = 17; i <= sheet.getLastRowNum(); i++) {
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
        String [][] dataUnwanted = new String[menurun][mendatarfix];
        menurun = 0;
        for (int i = 17; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {
                String cellValue = dataFormat.formatCellValue(cell);
                dataUnwanted[menurun][mendatarfix] = cellValue;
                mendatar++;
            }
            mendatar = 0;
            menurun++;
        }
        int count = 0;
        for (int i = 0; i<dataUnwanted.length; i++){
            if (dataUnwanted[i][0].isEmpty())
                break;
            count++;
        }
        String [][] unwanted = new String[count][3];
        for (int i = 0; i <unwanted.length;i++){
            for (int j = 0;j<unwanted[i].length; j++){
                unwanted[i][j] = dataUnwanted[i][j];
            }
        }
        return unwanted;
    }



}


