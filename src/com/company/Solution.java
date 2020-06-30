package com.company;

import com.sun.org.apache.xerces.internal.dom.PSVIElementNSImpl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class Solution {
    public int[][] solution;

    public Solution(int[][] probSolution) {
        solution = probSolution;
    }

    public double totalPenalty() {
        return penaltySC1() + penaltySC2() + penaltySC3() + penaltySC4() + penaltySC5() + penaltySC6() + penaltySC7() + penaltySC8() + penaltySC9();
//        System.out.println(totalPenalty());
    }


    private double penaltySC1() {
        double penaltySc1 = 0;
        for (int i = 0; i < 3; i++) {
            int day = 0;
            int softCons = -1;
            if (i == 0) {
                softCons = Main.constraints.getSc1_1();
                if (Main.constraints.getSc1_1() != 0) {
                    day = (Main.plannedDay) - (Main.constraints.getSc1_1());
                } else
                    continue;
            }
            if (i == 1) {
                softCons = Main.constraints.getSc1_2();
                if (Main.constraints.getSc1_2() != 0) {
                    day = (Main.plannedDay) - (Main.constraints.getSc1_2());
                } else
                    continue;
            }
            if (i == 2) {
                softCons = Main.constraints.getSc1_3();
                if (Main.constraints.getSc1_3() != 0) {
                    day = (Main.plannedDay) - (Main.constraints.getSc1_3());
                } else
                    continue;
            }
            for (int j = 0; j < Main.employees.length; j++) {
                for (int k = 0; k < day; k++) {
                    int count = (softCons + 1);
                    for (int l = k; l <= k + softCons; l++) {
                        if (solution[j][l] != 0) {
                            if (Main.shifts[solution[j][l] - 1].getShiftCat() == (i + 1)) {
                                count--;
                            }
                        }
                    }
                    if (count == 0)
                        penaltySc1 = penaltySc1 + 1;
                }
            }
        }
        return penaltySc1;

    }

    private double penaltySC2() {
        double penaltySc2 = 0;
        int softCons = Main.constraints.getSc2();
        if (softCons != 0) {
            int day = (Main.plannedDay) - softCons;
            for (int i = 0; i < Main.employees.length; i++) {
                for (int j = 0; j < day; j++) {
                    int count = (softCons + 1);
                    for (int k = j; k <= j + softCons; k++) {
                        if (solution[i][k] > 0)
                            count--;
                    }
                    if (count == 0)
                        penaltySc2 = penaltySc2 + 1;
                }
            }
        }
        return penaltySc2;
    }

    private double penaltySC3() {
        double penaltySc3 = 0;
        for (int i = 0; i < 3; i++) {
            int day = 0;
            int softCons = -1;
            if (i == 0) {
                softCons = Main.constraints.getSc3_1();
                if (Main.constraints.getSc3_1() != 0) {
                    day = (Main.plannedDay) - (Main.constraints.getSc3_1());
                } else
                    continue;
            }
            if (i == 1) {
                softCons = Main.constraints.getSc3_2();
                if (Main.constraints.getSc3_2() != 0) {
                    day = (Main.plannedDay) - (Main.constraints.getSc3_2());
                } else
                    continue;
            }
            if (i == 2) {
                softCons = Main.constraints.getSc3_3();
                if (Main.constraints.getSc3_3() != 0) {
                    day = (Main.plannedDay) - (Main.constraints.getSc3_3());
                } else
                    continue;
            }
            for (int j = 0; j < Main.employees.length; j++) {
                for (int k = 0; k < day; k++) {
                    if (solution[j][k + 1] != 0) {
                        if (Main.shifts[solution[j][k + 1] - 1].getShiftCat() == (i + 1)) {
                            if (solution[j][k] == 0 || Main.shifts[solution[j][k] - 1].getShiftCat() != (i + 1)) {
                                double countAgain = 0;
                                for (int l = k + 1; l <= k + softCons; l++) {
                                    int count = 1;
                                    for (int m = k + 1; m <= k + softCons; m++) {
                                        if (solution[j][m] == 0 || Main.shifts[solution[j][m] - 1].getShiftCat() != (i + 1))
                                            count = 0;
                                    }
                                    if (count == 1)
                                        countAgain = countAgain + 1;
                                }
                                double penalty3 = softCons - countAgain;
                                penaltySc3 = penaltySc3 + penalty3;
                            }
                        }
                    }
                }
            }
        }
        return penaltySc3;
    }

    private double penaltySC4() {
        double penaltySc4 = 0;
        int softCons = Main.constraints.getSc4();
        if (softCons != 0) {
            int day = (Main.plannedDay) - softCons;
            for (int i = 0; i < Main.employees.length; i++) {
                for (int j = 0; j < day; j++) {
                    if (solution[i][j + 1] != 0 && solution[i][j] == 0) {
                        double countAgain = 0;
                        for (int k = j + 1; k <= j + softCons; k++) {
                            int count = 1;
                            for (int l = j + 1; l <= j + softCons; l++) {
                                if (solution[i][l] == 0)
                                    count = 0;
                            }
                            if (count == 1)
                                countAgain = countAgain + 1;
                        }
                        double penalty4 = softCons - countAgain;
                        penaltySc4 = penaltySc4 - penalty4;
                    }
                }
            }
        }
        return penaltySc4;
    }

    private double penaltySC5() {
        double penaltySc5 = 0;
        for (int i = 0; i < 3; i++) {
            int min = 0;
            int max = 0;
            if (i == 0) {
                min = Main.constraints.getSc5_1Min();
                max = Main.constraints.getSc5_1Max();
            }
            if (i == 1) {
                min = Main.constraints.getSc5_2Min();
                max = Main.constraints.getSc5_2Max();
            }
            if (i == 2) {
                min = Main.constraints.getSc5_3Min();
                max = Main.constraints.getSc5_3Max();
            }
            if (min == 0 && max == 0)
                continue;

            for (int j = 0; j < Main.employees.length; j++) {
                int count = 0;
                for (int k = 0; k < Main.plannedDay; k++) {
                    if (solution[j][k] != 0)
                        if (Main.shifts[solution[j][k] - 1].getShiftCat() == (i + 1))
                            count++;
                }
                min = min - count;
                max = count - max;
                if (min > max && min > 0) {
                    penaltySc5 = penaltySc5 + (min * min);
                }
                if (max > min && max > 0) {
                    penaltySc5 = penaltySc5 + (max * max);
                }
            }
        }
        penaltySc5 = Math.sqrt(penaltySc5);
        return penaltySc5;

    }

    private double penaltySC6() {
        double penaltySc6 = 0;
        if (Main.constraints.getSc6()) {
            for (int i = 0; i < Main.employees.length; i++) {
                double hour = (Main.hourLimit[i][1]) * (Main.plannedHorizon[(Main.selectedFile - 1)]);
                double workingHour = 0;
                for (int j = 0; j < Main.plannedDay; j++) {
                    if (solution[i][j] != 0)
                        workingHour = workingHour + Main.shifts[solution[i][j] - 1].getDuration(j % 7);
                }
                hour = hour - workingHour;
                penaltySc6 = penaltySc6 + (hour * hour);
            }
            penaltySc6 = Math.sqrt(penaltySc6);
        }
        return penaltySc6;
    }

    private double penaltySC7() {
        double penaltySc7 = 0;
        if (Main.constraints.getSc7()) {
            for (int i = 0; i < Main.employees.length; i++) {
                int count = 0;
                for (int j = 0; j < (Main.plannedDay) - 1; j++) {
                    if (solution[i][j] != 0 && solution[i][j + 1] == 0)
                        count++;
                }
                penaltySc7 = penaltySc7 + (count * count);
            }
            penaltySc7 = Math.sqrt(penaltySc7);
        }
        return penaltySc7;
    }

    private double penaltySC8() {
        double penaltySc8 = 0;
        int day = (Main.plannedDay);
        int employee = Main.employees.length;
        if (Main.constraints.getSc8() != 0) {
            penaltySc8 = (double) employee * day;
            for (int i = 0; i < employee; i++) {
                for (int j = 0; j < day; j++) {
                    for (int k = 0; k < Main.thePattern.length; k++) {
                        if (day % 7 == Main.thePattern[k].startDay) {
                            if (solution[i][j] != 0) {
                                if (Main.shifts[solution[i][j] - 1].getShiftName().equals(Main.thePattern[k].shiftPattern[0])) {
                                    if (j <= day - (Main.thePattern[k].shiftPattern.length)) {
                                        int count = Main.thePattern[k].shiftPattern.length;
                                        for (int l = 0; l < Main.thePattern[k].shiftPattern.length; l++) {
                                            if (solution[i][j + l] != 0) {
                                                if (Main.shifts[solution[i][j + l] - 1].getShiftName().equals(Main.thePattern[k].shiftPattern[l])) {
                                                    count--;
                                                }
                                            } else {
                                                if (Main.thePattern[k].shiftPattern[l].equals("<Free>")) {
                                                    count--;
                                                }
                                            }
                                        }
                                        if (count == 0)
                                            penaltySc8 = penaltySc8 - 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return penaltySc8;
    }

    private double penaltySC9() {
        double penaltySc9 = 0;
        int day = (Main.plannedDay);
        int employee = Main.employees.length;
        if (Main.constraints.getSc9() != 0) {
            for (int i = 0; i < employee; i++) {
                for (int j = 0; j < day; j++) {
                    for (int k = 0; k < Main.notPattern.length; k++) {
                        if (day % 7 == Main.notPattern[k].startDay) {
                            if (solution[i][j] != 0) {
                                if (Main.shifts[solution[i][j] - 1].getShiftName().equals(Main.notPattern[k].shiftPattern[0])) {
                                    if (j <= day - (Main.notPattern[k].shiftPattern.length)) {
                                        int count = Main.notPattern[k].shiftPattern.length;
                                        for (int l = 0; l < Main.notPattern[k].shiftPattern.length; l++) {
                                            if (solution[i][j + l] != 0) {
                                                if (Main.shifts[solution[i][j + l] - 1].getShiftName().equals(Main.notPattern[k].shiftPattern[l])) {
                                                    count--;
                                                }
                                            } else {
                                                if (Main.notPattern[k].shiftPattern[l].equals("<Free")) {
                                                    count--;
                                                }
                                            }
                                        }
                                        if (count == 0)
                                            penaltySc9 = penaltySc9 - 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return penaltySc9;
    }

    public void SAGD() throws IOException {
        double desiredValue = 0;
        double decayrate = 0;
        double waterLevel = 0;
        int day = Main.plannedDay;
        int[][] newSolution = new int[solution.length][day];
        int[][] currentSolution = new int[solution.length][day];
        int[][] bestSolution = new int[solution.length][day];
        Main.cloneArray(solution, newSolution);
        Main.cloneArray(solution, currentSolution);
        Main.cloneArray(solution, bestSolution);
        double penalty = totalPenalty();
        long startTime = System.nanoTime();
        String selectedLLH = null;

//        for (int i = 0; i < 1000000; i++) {
//            int llh = (int) (Math.random() * 3);
//            switch (llh) {
//                case 0:
//                    Main.exchangeTwo(solution);
//                case 1:
//                    Main.exchangeThree(solution);
//                case 2:
////                    Main.double2Exchange(solution);
//            }
//        }
    }

    // GREAT DELUGE
    public void greatDeluge () throws IOException {
        int day = Main.plannedDay;
        long startTime = System.nanoTime();
        int[][] newSolution = new int[Main.employees.length][day];
        int[][] bestSolution = new int[Main.employees.length][day];
        Main.cloneArray(solution, newSolution);
        double initSolPenalty = totalPenalty();
        double waterLevel = totalPenalty();
        int iteration = 10000;
        double bestPenalty;
        double currPenalty;
        currPenalty = bestPenalty = totalPenalty();
        double alpha = 0.1;
        double desiredValue = 0;
        double decayRate = 0;
        int p = 0;
        double[][] plot = new double[100][4];

        for (int i = 0; i < iteration; i++) {
            int llh = (int) (Math.random() * 3);
            if (llh == 0)
                Main.exchangeTwo(solution);
            if (llh == 1)
                Main.exchangeThree(solution);
            if (llh == 2)
                Main.double2Exchange(solution);
            // currPenalty = totalPenalty();

            if (Main.checkAllHc(solution) == 0) {
                desiredValue = alpha * totalPenalty();
                decayRate = (waterLevel - desiredValue) / iteration;
                if (totalPenalty() <= currPenalty) {
                    currPenalty = totalPenalty();
                    waterLevel = currPenalty;
                    Main.cloneArray(solution, newSolution);
                } else {
                    if (totalPenalty() <= waterLevel) {
                        currPenalty = totalPenalty();
                        Main.cloneArray(solution, newSolution);
                    } else Main.cloneArray(newSolution, solution);
                }
                waterLevel = waterLevel - decayRate;
                }
            else Main.cloneArray(newSolution, solution);
            if ((i+1)%100 == 0) {
            System.out.println("GD - Iterasi ke - " + (i + 1) + " penaltinya " + totalPenalty() + " dengan waterlevel " + waterLevel);

            }
    }
            System.out.println("penaltinya " + totalPenalty());
            System.out.println("Nilai solusi awalnya : " + initSolPenalty);
//                    for (int j = 0; j < plot.length; j++) {
//                        for (int k = 0; k < plot[j].length; k++) {
//                            System.out.print(plot[j][k] + " ");
//                        }
//                        System.out.println();
//                    }

            Main.print(newSolution);
            long endTime = System.nanoTime();
            long time = (endTime - startTime) / 1000000000;
            //plot [100][0] = time;

            Main.print(newSolution);
            System.out.println(totalPenalty());
            DecimalFormat df = new DecimalFormat("#.###");
            String wkt = df.format(time);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String kapan = dateFormat.format(date);
            String finalPenalty = df.format(totalPenalty());
            String info = String.valueOf(iteration);

            if (Main.checkAllHc(solution) == 0) {
                System.out.println("Simpan solusi optimasi dengan Great Deluge?");
                System.out.println("1. Simpan");
                System.out.println("2. Batal");
                Scanner save = new Scanner(System.in);
                int choice = save.nextInt();
                if (choice == 1) {
                    Main.savingOptimizedSol(solution, Main.selectedFile);
                    //}
                } else
                    System.out.println("Penyimpanan dibatalkan ");

            }
        }




    public void hillClimbing () throws IOException {
            int day = Main.plannedDay;
            int[][] newSolution = new int[solution.length][day];
            double initSolPenalty = totalPenalty();
            int iteration = 1000000;
            double penalty = totalPenalty();
            Main.cloneArray(solution, newSolution);
            double[][] plot = new double[51][2];
            int p = 0;
            long startTime = System.nanoTime();

            for (int i = 0; i < iteration; i++) {
                int llh = (int) (Math.random() * 3);
                switch (llh) {
                    case 0:
                        Main.exchangeTwo(solution);
                    case 1:
                        Main.exchangeThree(solution);
                    case 2:
                        Main.double2Exchange(solution);
                }
                penalty = totalPenalty();
                if (Main.checkAllHc(solution) != 0) {
                    Main.cloneArray(newSolution, solution);
                } else {
                    if (penalty <= initSolPenalty) {
                        initSolPenalty = penalty;
                        Main.cloneArray(solution, newSolution);
                    } else {
                        Main.cloneArray(newSolution, solution);
                    }
                }

                if ((i+1)%5000 == 0)
                System.out.println((i + 1) + " " + initSolPenalty);

            }
            long endTime = System.nanoTime();
            double time_second = (endTime-startTime)/1000000000;
            //plot [100][0] = time;

          //  Main.print(newSolution);
            System.out.println(totalPenalty());
            DecimalFormat df = new DecimalFormat("#.###");
           DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            if(Main.checkAllHc(solution)==0) {
                System.out.println("Waktu yang diperlukan adalah " + time_second + " seconds");
                System.out.println("Simpan solusi optimasi dengan Hill Climbing?");
                System.out.println("1. Simpan");
                System.out.println("2. Batal");
                Scanner save = new Scanner(System.in);
                int choice = save.nextInt();
                if (choice == 1) {
                    Main.savingOptimizedSol(solution, Main.selectedFile);
                    //}
                } else
                    System.out.println("Penyimpanan dibatalkan");
            }
        }


//    public void selfAdaptive () throws IOException{
//        int day = Main.plannedDay;
//        double bestPenalty;
//        double currPenalty;
//        bestPenalty = currPenalty = totalPenalty();
//        String selectedLLH = null;
//        int iteration = 1000;
//        int [][] baseSolution = new int[solution.length][day];
//        int[][] newSolution = new int[solution.length][day];
//        Main.cloneArray(solution, baseSolution);
//        Main.cloneArray(solution,newSolution);
//        int [] antrianLLH = new int [iteration];
//
//        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(IterationQueue);
//
//        for (int i = 0; i < iteration; i++) {
//            int llh = (int) (Math.random() * 3);
//            if (llh == 0)
//                Main.exchangeTwo(solution);
//            if (llh == 1)
//                Main.exchangeThree(solution);
//            if (llh == 2)
//                Main.double2Exchange(solution);
//        }
//        for (int i = 0; i < iteration; i++) {
//            if (antrianLLH.length == 0) {
//                for (int j =0; j< iteration; j ++){
//                    int llh = (int) (Math.random() * 3);
//                    if (llh == 0)
//                        Main.exchangeTwo(solution);
//
//                    if (llh == 1)
//                        Main.exchangeThree(solution);
//                    if (llh == 2)
//                        Main.double2Exchange(solution);
//                                       }
//                if (Main.checkAllHc(solution) == 0{
//
//                }
//                }
//
//            }
//        }

    }






