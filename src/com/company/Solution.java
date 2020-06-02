package com.company;

public class Solution {
    public int[][] solution;

    public Solution(int[][] probSolution) {
        probSolution = solution;
    }

    public double totalPenalty() {
        return penalty1() + penalty2() + penalty3() + penalty4() + penalty5() + penalty6() + penalty7() + penalty7() + penalty8()+ penalty9();
//        System.out.println(totalPenalty());
    }


    private double penalty1() {
        double penalty1 = 0;
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
                    int count = (softCons+1);
                    for (int l = k; l <= k+softCons ; l++) {
                        if (solution[j][l] != 0) {
                            if (Main.shifts[solution[j][l]-1].getShiftCat() == (i+1)) {
                                count--;
                            }
                        }
                    }
                    if (count == 0)
                        penalty1 = penalty1 + 1;
                }
            }
        }
        return penalty1;

    }
    private double penalty2() {
        double penalty2 = 0;
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
                        penalty2 = penalty2 + 1;
                }
            }
        }
        return penalty2;
    }
    private double penalty3() {
    double penalty3 = 0;
    }
    private double penalty4() {
        double penalty4 = 0;
    }
    private double penalty5() {
        double penalty5 = 0;
    }
    private double penalty6() {
    double penalty6 = 0;

    }
    private double penalty7() {
        double penalty7 = 0;
    }
    private double penalty8() {
        double penalty8 = 0;
    }
    private double penalty9() {
        double penalty9 = 0;
    }


}
