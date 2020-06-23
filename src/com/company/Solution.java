package com.company;

import com.sun.org.apache.xerces.internal.dom.PSVIElementNSImpl;

import java.io.IOException;
import java.text.ParseException;

public class Solution {
    public int[][] solution;

    public Solution(int[][] probSolution) {
        probSolution = solution;
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
                    day = (Main.plannedHorizon[(Main.selectedFile - 1)] * 7) - (Main.constraints.getSc1_1());
                } else
                    continue;
            }
            if (i == 1) {
                softCons = Main.constraints.getSc1_2();
                if (Main.constraints.getSc1_2() != 0) {
                    day = (Main.plannedHorizon[(Main.selectedFile - 1)] * 7) - (Main.constraints.getSc1_2());
                } else
                    continue;
            }
            if (i == 2) {
                softCons = Main.constraints.getSc1_3();
                if (Main.constraints.getSc1_3() != 0) {
                    day = (Main.plannedHorizon[(Main.selectedFile - 1)] * 7) - (Main.constraints.getSc1_3());
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
            int day = (Main.plannedHorizon[Main.selectedFile - 1] * 7) - softCons;
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
                    day = (Main.plannedHorizon[(Main.selectedFile - 1)] * 7) - (Main.constraints.getSc3_1());
                } else
                    continue;
            }
            if (i == 1) {
                softCons = Main.constraints.getSc3_2();
                if (Main.constraints.getSc3_2() != 0) {
                    day = (Main.plannedHorizon[(Main.selectedFile - 1)] * 7) - (Main.constraints.getSc3_2());
                } else
                    continue;
            }
            if (i == 2) {
                softCons = Main.constraints.getSc3_3();
                if (Main.constraints.getSc3_3() != 0) {
                    day = (Main.plannedHorizon[(Main.selectedFile - 1)] * 7) - (Main.constraints.getSc3_3());
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
            int day = (Main.plannedHorizon[(Main.selectedFile - 1)] * 7) - softCons;
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
                for (int k = 0; k < Main.plannedHorizon[(Main.selectedFile - 1)] * 7; k++) {
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
                for (int j = 0; j < Main.plannedHorizon[(Main.selectedFile - 1)] * 7; j++) {
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
                for (int j = 0; j < (Main.plannedHorizon[(Main.selectedFile - 1)] * 7) - 1; j++) {
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
        int day = (Main.plannedHorizon[(Main.selectedFile - 1)] * 7);
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
        int day = (Main.plannedHorizon[(Main.selectedFile - 1)] * 7);
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

    public void greatDeluge () throws IOException {
        for (int a = 0; a < 9; a++) {
            int day = Main.plannedDay;
            int[][] newSolution = new int[solution.length][day];
            int [][] currentSolution = new int[solution.length][day];
            int [][] bestSolution = new int [solution.length][day];
            double [][] boxplot = new double[101][2];
       // int p = 0;
            int decayrate = 10;
            int [] iteration = new int [1000000];
            double newPenalty =  totalPenalty();
            double waterLevel = 0;
            double theBest = 0;

            double penalty = totalPenalty();
            Main.cloneArray(solution, newSolution);
            Main.cloneArray(solution, currentSolution);
            long startTime = System.nanoTime();
            for (int i = 0; i < 10000; i++){
                int llh = (int) (Math.random() * 3);
                switch (llh) {
                    case 0:
                        Main.exchangeTwo(solution);
                    case 1:
                        Main.exchangeThree(solution);
                    case 2:
                        Main.double2Exchange(solution);
                }
                // Penjumlahan penalty

                // Solusi lebih baik. untuk solusi lebih baik maka waterLevel sama dengan nilai solusi yang sekarang
                 if (totalPenalty() < newPenalty){
                     newPenalty = totalPenalty();
                     waterLevel = newPenalty;
                     Main.cloneArray(solution, newSolution); //copy solusinya
                     }
                 // Solusi lebih baik atau sama dengan
                 else if (newPenalty <= waterLevel){
                     newPenalty = totalPenalty() ;
                     Main.cloneArray(solution, newSolution);
                 }
                 // Solusi lebih buruk, tidak diterima
                 else {
                     Main.cloneArray(newSolution, solution);
                 }
                 // Solusi paling baik
                 if (newPenalty < theBest) {
                     theBest = newPenalty;
                     Main.cloneArray(bestSolution, newSolution);
                     long gdBestTime = System.nanoTime();
                 }
                     waterLevel = waterLevel-decayrate; // update waterlevel tiap iterasi

//                       }

     }
                long endTime = System.nanoTime();
                long time = (endTime-startTime)/1000000000 ;
                Main.savingOptimizedSol(boxplot,a);
                System.out.println("nilai terbaik " + theBest);
                Main.cloneArray(bestSolution, solution);
        }
    }
}


