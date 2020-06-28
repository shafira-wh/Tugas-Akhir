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
    public static double[][] hourLimit;
    public static Scanner input;
    public static String[][] wantedPattern;
    public static Pattern[] thePattern;
    public static String[][] unwantedPattern;
    public static Pattern[] notPattern;
    public static int selectedProcess;
    public static int selectedFile;
    public static int [] plannedHorizon;
    public static int diff;
    public static int plannedDay;
    public static Scanner save;

    public static String optimizedPath;


    public static void main(String[] args) throws IOException, ParseException {

        System.out.println("Penjadwalan Perawat Dataset Benchmark Rumah Sakit di Norwegia");
        System.out.println("1. Solusi Awal");
        System.out.println("2. Optimasi");
        System.out.println("Pilih proses yang diinginkan");
        Scanner input = new Scanner(System.in);
        selectedProcess = input.nextInt();

        for (int i = 0; i < 7; i++) {
            System.out.println((i + 1) + ". OptTur" + (i + 1));
        }

        System.out.println("Pilih file :");
        selectedFile = input.nextInt();
        if (selectedProcess == 1 )
        System.out.println("File yang terpilih adalah OpTur"+ selectedFile + " untuk proses pembuatan Solusi awal");
        else
            System.out.println("File yang terpilih adalah OpTur"+ selectedFile + " untuk Optimasi Solusi awal");

        optimizedPath = "D:\\Kuliah!\\Semester 8\\TA\\Solution\\OpTur" + selectedFile + ".txt";
        filePath = "D:\\Kuliah!\\Semester 7\\PTA\\Nurse Rostering Data\\OpTur" + (selectedFile) + ".xls";

        String[][] readEmployee = sheetEmployee().clone();
        String[][] readShift = sheetShift().clone();
        String[][] readManpower = sheetManpower().clone();
        String[] readConstraint = sheetConstraint().clone();
        wantedPattern = sheetWanted().clone();
        unwantedPattern = sheetUnwanted().clone();

//diff hour
        if (selectedFile==7) {
            diff = 2;
        }
        if (selectedFile ==3){
            diff =1 ;
        }
        if (selectedFile != 7  && selectedFile != 3) {
            diff = 1;
        }

//planned horizon yang feasible sementara optur457
        if (selectedFile== 1){
            plannedDay = 84;
        }
        if (selectedFile== 2){
            plannedDay = 42;
        }
        if (selectedFile== 3){
            plannedDay = 42;
        }
        if (selectedFile== 4){
            plannedDay = 168;
        }
        if (selectedFile==5){
            plannedDay = 28;
        }
        if (selectedFile==6){
            plannedDay = 84;
        }
        if (selectedFile== 7){
            plannedDay = 42;
        }


 int [] plannedWeek = {12,6,6,24,4,12,6};
        plannedHorizon = plannedWeek.clone();
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
        constraints.setHc1(); constraints.setHc2(); constraints.setHc3();
        constraints.setHc4(); constraints.setHc5_1(); constraints.setHc5_2();
        constraints.setHc5_3(); constraints.setHc5_4(); constraints.setHc5_5();
        constraints.setHc5_6(); constraints.setHc6(); constraints.setHc7();
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
        for (int i = 0; i < shifts.length; i++) {
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
        }
        shiftWday = new int[count][2];
        count = 0;
        for (int i = 0; i < shifts.length; i++) {
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
            }
// Pasangan Shift yang tidak boleh pada Weekend
        count = 0;
        for (int i = 0; i < shifts.length; i++) {
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
        }

        shiftWend = new int[count][2];
        count = 0;
        for (int i = 0; i < shifts.length; i++) {
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


        hourLimit = new double[employees.length][7];
        for (int i = 0; i < hourLimit.length; i++) {
            hourLimit[i][0] = employees[i].getWorkHour() - (employees[i].getWorkHour() * constraints.getHc3());
            hourLimit[i][1] = employees[i].getWorkHour();
            hourLimit[i][2] = employees[i].getWorkHour() + (employees[i].getWorkHour() * constraints.getHc3());
        }
        if (selectedProcess == 1)
            initialSolution();
        if (selectedProcess == 2) ;
        optimizedSolution();

    }


    public static void initialSolution() throws IOException {
        int[][] matrix_solution = new int[employees.length][plannedDay];
        for (int i = 0; i < matrix_solution.length; i++)
            for (int j = 0; j < matrix_solution[i].length; j++)
                matrix_solution[i][j] = 0;

        for (int i = 5; i < plannedDay; i = i + 7) {
            while (!validDay(matrix_solution, i)) {
                int shift = isMissing(matrix_solution, i);
                int randomEmp = (int) (Math.random() * employees.length);
                if (assignHC2(matrix_solution, i, shift, randomEmp))
                    matrix_solution[randomEmp][i] = shift;
            }
        }
        for (int i = 6; i < plannedDay; i = i + 7) {
            while (!validDay(matrix_solution, i)) {
                int shift = isMissing(matrix_solution, i);
                int randomEmp = (int) (Math.random() * employees.length);
                if (assignHC2(matrix_solution, i, shift, randomEmp))
                    matrix_solution[randomEmp][i] = shift;
            }
        }
        for (int i = 0; i < plannedDay; i++) {
            if (i % 7 == 5 || i % 7 == 6)
                continue;
            while (!validDay(matrix_solution, i)) {
                int shift = isMissing(matrix_solution, i);
                int randomEmp = (int) (Math.random() * employees.length);
                if (assignHC2(matrix_solution, i, shift, randomEmp))
                    matrix_solution[randomEmp][i] = shift;
            }
        }
        int count = countHC6(matrix_solution);
        System.out.println(count);
        //tempSol = temporary solution matrix
        int[][] tempMatrixSol = new int[employees.length][plannedDay];
        cloneArray(matrix_solution, tempMatrixSol);
        double hour_solution = hourDifference(matrix_solution);
        for (int i = 0; i < 50000; i++) {
            int llh = (int) (Math.random() * 3);
            if (llh == 0)
                exchangeTwo(tempMatrixSol);
            if (llh == 1)
                exchangeThree(tempMatrixSol);
            if (llh == 2)
                double2Exchange(tempMatrixSol);
            if (checkHC4Com(tempMatrixSol)) {
                if (checkHC5(tempMatrixSol)) {
                    if (checkHC7(tempMatrixSol)) {
                        if (hourDifference(tempMatrixSol) <= hour_solution) {
                            if (countHC6(tempMatrixSol) <= count) {
                                cloneArray(tempMatrixSol, matrix_solution);
                                count = countHC6(tempMatrixSol);
                                hour_solution = hourDifference(tempMatrixSol);
                            } else {
                                cloneArray(matrix_solution, tempMatrixSol);
                            }
                        } else {
                            cloneArray(matrix_solution, tempMatrixSol);
                        }
                    } else {
                        cloneArray(matrix_solution, tempMatrixSol);
                    }
                } else {
                    cloneArray(matrix_solution, tempMatrixSol);
                }
            } else {
                cloneArray(matrix_solution, tempMatrixSol);
            }
            System.out.println("Pada iterasi ke " + (i + 1) + " " + hour_solution);
        }
        //
        if (checkHC2(matrix_solution)) {
            if (checkHC3(matrix_solution)) {
                if (checkHC4Com(matrix_solution)) {
                    if (checkHC5(matrix_solution)) {
                        if (checkHC6(matrix_solution)) {
                            if (checkHC7(matrix_solution)) {
                            } else {
                                int[][] temporarySolMat = new int[employees.length][plannedDay];
                                cloneArray(matrix_solution, temporarySolMat);

                                int iterate = 0;
                                do {
                                    System.out.println("Iterasi ke " + (iterate + 1) + " pada do while");
                                    iterate++;
                                    int llh = (int) (Math.random() * 3);
//                                    System.out.println("sudah sampek sini" + llh);
                                    if (llh == 0)
                                        exchangeTwo(temporarySolMat);
                                    if (llh == 1)
                                        exchangeThree(temporarySolMat);
                                    if (llh == 2)
                                        double2Exchange(temporarySolMat);
                                    //System.out.println("sudah sampek sini");
                                    if (checkHC3(temporarySolMat)) {
                                        if (checkHC4Com(temporarySolMat)) {
                                            if (checkHC5(temporarySolMat)) {
//                                                if (checkHC7(matrixsolTemp2)) {
                                                if (countHC6(temporarySolMat) <= count) {
                                                    count = countHC6(temporarySolMat);
                                                    cloneArray(temporarySolMat, matrix_solution);
                                                } else
                                                    cloneArray(matrix_solution, temporarySolMat);
                                            } else
                                                cloneArray(matrix_solution, temporarySolMat);
                                        } else
                                            cloneArray(matrix_solution, temporarySolMat);
                                    } else
                                        cloneArray(matrix_solution, temporarySolMat);
                                } while (!checkHC6(matrix_solution));
                            }
                        } else
                            System.out.println("Hard constraint 7 tidak feasible");
                    } else
                        System.out.println("Hard constraint 5 tidak feasible");
                } else
                    System.out.println("Hard constraint 4 tidak feasible");
            } else
                System.out.println("Hard constraint 3 tidak feasible");
        } else
            System.out.println("Hard constraint 2 tidak feasible");


        if (checkAllHc(matrix_solution) == 0) {
            Solution solution = new Solution(matrix_solution);
            print(matrix_solution);
            System.out.println("Nilai penalti pada solusi = " + solution.totalPenalty());
            System.out.println("1. Simpan");
            System.out.println("2. Batal");
            Scanner save = new Scanner(System.in);
            int choice = save.nextInt();
            if (choice == 1) {
                savingSolution(matrix_solution, selectedFile);
                savingShiftSol(matrix_solution,selectedFile);
            }

        }
        else {
            System.out.println("HC" + checkAllHc(matrix_solution)+ " tidak feasible");
        }
    }

    public static void optimizedSolution () throws IOException{
        int [][] matrix_solution = new int [employees.length][plannedDay];
        File fileOpt = new File(optimizedPath);

        BufferedReader read = new BufferedReader(new FileReader(fileOpt));

        String readline = "";
        int index = 0;
        while ((readline = read.readLine()) != null) {
            String [] tmp = readline.split(" ");
            for (int i = 0; i < matrix_solution[index].length; i++) {
                matrix_solution[index][i] = Integer.parseInt(tmp[i]);
            } index++;
        }
        Solution solution = new Solution(matrix_solution);
        solution.greatDeluge();
       // System.out.println(solution.totalPenalty());
    }

    public static void cloneArray (int[][] solution, int[][] tempSolution){
        for (int i = 0; i < tempSolution.length; i++) {
            for (int j = 0; j < tempSolution[i].length; j++) {
                tempSolution[i][j] = solution[i][j];
            }
        }
    }

    public static void print(int[][] printAll) {
        for (int i = 0; i < printAll.length; i++) {
            for (int j = 0; j < printAll[i].length; j++) {
                System.out.print(printAll[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int isMissing(int[][] solution, int day) {
        for (int i = 0; i < manpowers.length; i++)
            if (manpowers[i].getShiftNeeded(day % 7) > needs(solution, i + 1, day))
                return i + 1;
        return 0;
    }

    private static boolean validDay(int[][] solution, int day) {
        for (int i = 0; i < manpowers.length; i++) {
            if (manpowers[i].getShiftNeeded(day % 7) != needs(solution, i + 1, day))
                return false;
        }
        return true;
    }

    public static int checkAllHc(int[][] solution) {
        if (!checkHC2(solution))
            return 2;
        if (!checkHC3(solution))
            return 3;
        if (!checkHC4Com(solution))
            return 4;
        if (!checkHC5(solution))
            return 5;
        if (!checkHC6(solution))
            return 6;
        if (!checkHC7(solution))
            return 7;
        return 0;

    }
// Low Level Heuristic 1
    public static void exchangeTwo(int[][] solution) {
        int randomDay = -1;
        do {
            randomDay = (int) (Math.random() * plannedDay);
        } while (randomDay % 7 == 5 || randomDay % 7 == 6);

        int randomEmp1 = -1;
        int randomEmp2 = -1;
//        int randomEmp1 = (int) (Math.random() * emp.length);
//        int randomEmp2 = (int) (Math.random() * emp.length);
        do {
            randomEmp1 = (int) (Math.random() * employees.length);
        } while (solution[randomEmp1][randomDay] == 0);

        do {
            randomEmp2 = (int) (Math.random() * employees.length);
        } while (solution[randomEmp2][randomDay] != 0);

        int shiftExchange = solution[randomEmp1][randomDay];
        solution[randomEmp1][randomDay] = solution[randomEmp2][randomDay];
        solution[randomEmp2][randomDay] = shiftExchange;
    }
    // Low Level Heuristic 2
    public static void exchangeThree(int[][] solution) {
        int randomDay = (int) (Math.random() * plannedDay);
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
    // Low Level Heuristic 3
    public static void double2Exchange(int[][] solution) {
        int randomEmp1 = -1;
        int randomEmp2 = -1;
        int randomDay1 = -1;
        int randomDay2 = -1;
        while (randomEmp1 == -1 || randomEmp2 == -1) {
            do {
                randomDay1 = (int) (Math.random() * plannedDay);
                randomDay2 = (int) (Math.random() * plannedDay);
            } while (randomDay1 % 7 == 5 || randomDay1 % 7 == 6 || randomDay2 % 7 == 5 || randomDay2 % 7 == 6);
            for (int i = 0; i < employees.length; i++) {
                for (int j = 0; j < employees.length; j++) {
                    if (solution[i][randomDay1] != 0 && solution[j][randomDay2] != 0 && shifts[solution[i][randomDay1] - 1].getShiftCat() == shifts[solution[j][randomDay2] - 1].getShiftCat())
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
// Menghitung jam kerja selama satu minggu
    public static double[] allWorkHour(int[][] solution, int week) {
        double[] workhour_week = new double[employees.length];
        for (int i = 0; i < employees.length; i++) {
            for (int j = week * 7; j < (week + 1) * 7; j++) {
                if (solution[i][j] != 0) {
                    workhour_week[i] = workhour_week[i] + shifts[solution[i][j] - 1].getDuration(j % 7);
                }
            }
        }
        return workhour_week;
    }

    public static double[] averageWorkHour(int[][] solution, int week) {
        double[] avg = new double[employees.length];

        for (int i = 0; i < week; i++) {
            for (int j = 0; j < employees.length; j++) {
                avg[j] = avg[j] + allWorkHour(solution, i)[j];
            }
        }
        for (int i = 0; i < employees.length; i++) {
            avg[i] = (avg[i] / week);
        }
        return avg;
    }


    public static int needs(int[][] solution, int shift, int day) {
        int count = 0;
        for (int i = 0; i < solution.length; i++) {
            if (solution[i][day] == shift)
                count++;
        }
        return count;
    }

    public static double hourDifference (int [][] solution){
        double hour = 0;
        double[] startHour = averageWorkHour(solution, plannedHorizon[selectedFile-1]).clone();
        for (int i=0; i < startHour.length;i++){
            hour = hour + (double)(Math.abs(startHour[i]-hourLimit[i][diff]));

        }
        return hour;
    }

    public static boolean assignHC2(int[][] solution, int day, int shift, int employee) {
        if (manpowers[shift - 1].getShiftNeeded(day % 7) > needs(solution, shift, day))
            if (assignHC4Com(shift, employee)) {
                if (assignHC4Weekend(day, employee)) {
                    if (assignHC7(solution,day,shift, employee)) {
                        if (assignHC5(solution, day, shift, employee)) {
                            return true;
                        }
                    }
                }
            }
        return false;
    }


    public static boolean assignHC4Com(int shift, int employee) {
        if (shifts[shift - 1].getCompetence().equals("A") && employees[employee].getCompetence().equals("")) {
            return false;
        }
        return true;
    }

    public static boolean assignHC4Weekend(int day, int employee) {
        if (day % 7 == 5 || day % 7 == 6) {
            for (int i = 0; i < employees[employee].getWorkWeekend().length; i++) {
                if (day / 7 == employees[employee].getWorkWeekend()[i] - 1)
                    return true;
            }
        } else return true;
        return false;
    }


    public static boolean assignHC5(int[][] solution, int day, int shift, int employee) {
        if (day == 0)
            return true;

        if (day % 7 == 5 || day % 7 == 6) {
            for (int i = 0; i < shiftWend.length; i++) {
                if (solution[employee][day - 1] == shiftWend[i][0] && shift == shiftWend[i][1]) {
                    return false;
                }
                if (day < (plannedDay) - 1) {
                    if (solution[employee][day + 1] == shiftWend[i][1] && shift == shiftWend[i][0])
                        return false;
                }
            }

        }
        if (day % 7 == 0 || day % 7 == 1 || day % 7 == 2 || day % 7 == 3 || day % 7 == 4) {
            for (int i = 0; i < shiftWday.length; i++) {
                if (solution[employee][day - 1] == shiftWday[i][0] && shift == shiftWday[i][1]) {
                    return false;
                }
                if (day < (plannedDay) - 1) ;
                {
                    if (solution[employee][day + 1] == shiftWday[i][1] && shift == shiftWday[i][0])
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
        label:
        for (int j = 0; j < employees.length; j++) {
            for (int i = 7 * week; i < (week + 1) * 7; i++) {
                if (solution[j][i] == 0) {
                    if (i == week * 7) {
                        if (solution[j][i + 1] == 0) {
                            isOk[j] = true;
                            continue label;
                        } else {
                            if (shifts[solution[j][i + 1] - 1].getShiftCat() != 1) {
                                isOk[j] = true;
                                continue label;
                            } else {
                                LocalTime lessthan = LocalTime.parse("08:00");
                                if (shifts[solution[j][i + 1] - 1].getStartTime().isAfter(lessthan)) ;
                                {
                                    isOk[j] = true;
                                    continue label;
                                }
                            }
                        }
                    }
                    if (i > week * 7 && i < ((week + 1) * 7) - 1) {
                        if (solution[j][i + 1] == 0 || solution[j][i - 1] == 0) {
                            isOk[j] = true;
                            continue label;
                        }
                        if (solution[j][i + 1] != 0 && shifts[solution[j][i + 1] - 1].getShiftCat() != 1) {
                            isOk[j] = true;
                            continue label;
                        }
                        if (solution[j][i + 1] != 0 && shifts[solution[j][i + 1] - 1].getShiftCat() == 1) {
                            if (shifts[solution[j][i - 1] - 1].getShiftCat() != 3) {
                                LocalTime lessthan = LocalTime.parse("08:00");
                                LocalTime limit = LocalTime.parse("00:00");
                                LocalTime before = limit.minusHours(shifts[solution[j][i - 1] - 1].getEndTime().getHour()).minusMinutes(shifts[solution[j][i - 1] - 1].getEndTime().getMinute());
                                LocalTime total = before.plusHours(shifts[solution[j][i + 1] - 1].getStartTime().getHour()).minusMinutes(shifts[solution[j][i + 1] - 1].getStartTime().getMinute());
                                if (total.isAfter(lessthan)) {
                                    isOk[j] = true;
                                    continue label;
                                }
                            }
                        }
                    }
                    if (i == ((week + 1) * 7) - 1) {
                        if (solution[j][i - 1] == 0) {
                            isOk[j] = true;
                            continue label;
                        } else {
                            LocalTime lessthan = LocalTime.parse("08:00");
                            LocalTime limit = LocalTime.parse("00:00");
                            LocalTime before = limit.minusHours(shifts[solution[j][i - 1] - 1].getEndTime().getHour()).minusMinutes(shifts[solution[j][i - 1] - 1].getEndTime().getMinute());
                            if (before.isAfter(lessthan)) {
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




    public static boolean assignHC7(int[][] solution, int day, int shift, int employee) {
        double sumTotal = shifts[shift - 1].getDuration(day % 7);
        if (sumTotals(solution, day, employee) + sumTotal <= constraints.getHc7()) {
            return true;
        }
        return false;
    }

    public static double sumTotals(int[][] solution, int day, int employee) {
        double count = 0;
        int week = day / 7;
        for (int i = week * 7; i < (week + 1); i++) {
            if (solution[employee][i] != 0) {
                count = count + shifts[solution[employee][i] - 1].getDuration(day % 7);
            }
        }
        return count;
    }

    public static boolean checkHC2(int[][] solution) {
        for (int i = 0; i < plannedDay; i++) {
            for (int j = 0; j < manpowers.length; j++) {
                if (manpowers[j].getShiftNeeded(i % 7) != needs(solution, j + 1, i))
                    return false;
            }
        }
        return true;
    }

    public static boolean checkHC3(int[][] solution) {
        double[] hourWork = averageWorkHour(solution, plannedHorizon[selectedFile - 1]).clone();
        for (int i = 0; i < hourWork.length; i++) {
            if (hourWork[i] > hourLimit[i][2])
                return false;
            if (hourWork[i] < hourLimit[i][0])
                return false;
        }
        return true;
    }

    public static boolean checkHC4Com(int[][] solution) {
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                if (solution[i][j] != 0) {
                    if (shifts[solution[i][j] - 1].getCompetence().equals("A") && employees[i].getCompetence().equals("")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
public static boolean checkHC4Weekend (int [][] solution, int employee) {
    for (int i = 0; i < plannedDay; i++) {
        if (i % 7 == 5 || i % 7 == 6) {
            if (solution[employee][i] != 0) {
                for (int j = 0; j < employees[employee].getWorkWeekend().length; j++) {
                    if (i / 7 != employees[employee].getWorkWeekend()[j] - 1)
                        return false;
                }
                return true;
            }
        }
    }
    return true;
}
public static boolean checkHC5 (int [][] solution){
    for (int i = 0; i < plannedDay; i++) {
        for (int j = 0; j < employees.length; j++) {
            if (i % 7 != 5 || i % 7 != 6) {
                for (int a = 0; a < shiftWday.length; a++) {
                    if (i < (plannedDay) - 1)
                        if (solution[j][i] == shiftWday[a][0] && solution[j][(i+1)] == shiftWday[a][1])
                            return false;
                }
            }
            else {
                for (int a = 0; a < shiftWend.length; a++) {
                    if (i < (plannedDay) - 1)
                        if (solution[j][i] == shiftWend[a][0] && solution[j][(i+1)] ==  shiftWend[a][1])
                            return false;
                }
            }
        }
    }
    return true;
}
    public static boolean checkHC6(int[][] solution) {
        boolean[][] check = new boolean[employees.length][plannedHorizon[selectedFile - 1]];
        for (int i = 0; i < plannedHorizon[selectedFile - 1]; i++) {
            boolean[] check2 = checkFreeWeekend(solution, i).clone();
            {
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
    public static int countHC6 (int [][] solution) {
        int count = 0;
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
                if (check[i][j] == false) {
                    count++;
                }
            }
        }
        return count;
    }

public static boolean checkHC7 (int [][] solution){
        for (int i=0;i< plannedDay; i++){
            for (int j=0; j< employees.length; j++){
                if (sumTotals(solution, i,j) > constraints.getHc7()) {
                    return false;
                }
            }
        }
    return true;
    }

    // Menyimpan solusi ke .txt
    public static void savingSolution (int [][] solution, int number) throws IOException {
        FileWriter savedSol = new FileWriter("D:\\Kuliah!\\Semester 8\\TA\\Solution\\OpTur" + selectedFile + ".txt", false);
//        try (BufferedWriter savedSol = new BufferedWriter(new FileWriter(filePath))) {
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                    savedSol.write(solution[i][j] + " ");
                }
                savedSol.write("\n");
            }
            savedSol.close();
        }



    // Menyimpan solusi shift ke .txt
    public static void savingShiftSol (int [][] solution, int number ) throws IOException {

        FileWriter savedShiftSol = new FileWriter("D:\\Kuliah!\\Semester 8\\TA\\Solution\\Shifts OpTur" + selectedFile + ".txt", false);
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                if (solution[i][j] != 0)
                    savedShiftSol.write(shifts[solution[i][j]-1].getShiftName() + " ");
                else
                    savedShiftSol.write("<Free>" + " ");
            } savedShiftSol.write("\n");
        }
        savedShiftSol.close();
    }
    // Menyimpan optimasi ke .txt
    public static void savingOptimizedSol (double [][] solution, int number) throws IOException {
        FileWriter savedOptSol = new FileWriter("D:\\Kuliah!\\Semester 8\\TA\\Solution\\Optimasi  OpTur" + selectedFile + ".txt", false);
        for (int i=0; i <solution.length; i++){
            for (int j=0; j<solution[i].length;j++){
                savedOptSol.write(solution[i][j] + " ");
            }
            savedOptSol.write("\n");
        }
        savedOptSol.close();
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
                 //System.out.print(cellValue + "\n");
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
//        System.out.print(dataConstraint.length);
//        System.out.println(mendatarfix);
//        System.out.println(menurun);
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
        for (int i = 4; i <= 12; i++) {
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
        for (int i = 4; i <= 12; i++) {
            row = sheet.getRow(i);
            for (Cell cell : row) {
                String cellValue = dataFormat.formatCellValue(cell);
                dataWanted[menurun][mendatar] = cellValue;
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
                dataUnwanted[menurun][mendatar] = cellValue;
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
//
}


