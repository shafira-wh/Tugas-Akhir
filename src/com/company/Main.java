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

public class Main {
    /*  public static final String file = "D:\\Kuliah!\\Semester 7\\PTA\\Nurse Rostering Data\\OpTur1.xls";
      public static final String file = "D:\\Kuliah!\\Semester 7\\PTA\\Nurse Rostering Data\\OpTur2.xls";
      public static final String file = "D:\\Kuliah!\\Semester 7\\PTA\\Nurse Rostering Data\\OpTur3.xls";
      public static final String file = "D:\\Kuliah!\\Semester 7\\PTA\\Nurse Rostering Data\\OpTur4.xls";
      public static final String file = "D:\\Kuliah!\\Semester 7\\PTA\\Nurse Rostering Data\\OpTur5.xls";
      public static final String file = "D:\\Kuliah!\\Semester 7\\PTA\\Nurse Rostering Data\\OpTur6.xls";
     */
    public static final String file = "D:\\Kuliah!\\Semester 7\\PTA\\Nurse Rostering Data\\OpTur7.xls";
    public static Manpower[] manpowers;
    public static Employee[] employees;
    public static Shift[] shifts;
    public static Constraint constraints;
    public static int  [][] shiftWday;
    public static int [][] shiftWend;


    public static void main(String[] args) throws IOException, ParseException {


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
        for (int s = 0; s < read1.length; s++) {
            shifts[s] = new Shift(
                    Integer.parseInt(read1[s][0]),
                    Double.parseDouble(read1[s][1]), Double.parseDouble(read1[s][2]),
                    Double.parseDouble(read1[s][3]), Double.parseDouble(read1[s][4]),
                    Double.parseDouble(read1[s][5]), Double.parseDouble(read1[s][6]),
                    Double.parseDouble(read1[s][7]), Integer.parseInt(read1[s][8]),
                    (read1[s][9]), (times[s][0]), (times[s][1]), (read1[s][14]));
        }


//        for (int i = 0; i < shifts.length; i++) {
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
//                            shifts[i].getEndTime() + " " +
//                            shifts[i].getCompetence() + " ");
//        }


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

        int count = 0;
        for (int i = 0; i < shifts.length; i++) {
            for (int j = 0; j < shifts.length; j++) {
                if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 2) {
                    if (constraints.getHc5_1().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0)
                        count++;
                }
                if (shifts[i].getShiftCat() == 2 && shifts[j].getShiftCat() == 1) {
                    if (constraints.getHc5_3().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0)
                        count++;
                }
                if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 1) {
                    if (constraints.getHc5_6().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0)
                        count++;
                }
            }
        }

        shiftWday = new int[count][2];
        count = 0;
        for (int i = 0; i < shifts.length; i++) {
            for (int j = 0; j < shifts.length; j++) {
                if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 2) {
                    if (constraints.getHc5_1().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0) {
                        shiftWday[count][0] = i + 1;
                        shiftWday[count][0] = j + 1;
                        count++;
                    }
                }
                if (shifts[i].getShiftCat() == 2 && shifts[j].getShiftCat() == 1) {
                    if (constraints.getHc5_3().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0) {
                        shiftWday[count][0] = i + 1;
                        shiftWday[count][0] = j + 1;
                        count++;
                    }
                }
                if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 1) {
                    if (constraints.getHc5_5().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0) {
                        shiftWday[count][0] = i + 1;
                        shiftWday[count][0] = j + 1;
                        count++;
                    }
                }

            }

        }

       shiftWend  = new int [count] [2];
        count = 0;
        for (int i = 0; i < shifts.length; i++) {
            for (int j = 0; j < shifts.length; j++) {
                if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 2) {
                    if (constraints.getHc5_2().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0) {
                        shiftWend[count][0] = i + 1;
                        shiftWend[count][0] = j + 1;
                        count++;
                    }
                }
                if (shifts[i].getShiftCat() == 2 && shifts[j].getShiftCat() == 1) {
                    if (constraints.getHc5_4().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0) {
                        shiftWday[count][0] = i + 1;
                        shiftWday[count][0] = j + 1;
                        count++;
                    }
                }
                if (shifts[i].getShiftCat() == 3 && shifts[j].getShiftCat() == 1) {
                    if (constraints.getHc5_6().compareTo(shifts[j].getStartTime().minusHours(shifts[i].getEndTime().getHour()).minusMinutes(shifts[i].getEndTime().getMinute())) > 0) {
                        shiftWend[count][0] = i + 1;
                        shiftWend[count][0] = j + 1;
                        count++;
                    }
                }

            }

        }
       // System.out.println(Arrays.toString(shiftWday));
       // System.out.println(shiftWend);
        initsol();
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
                                        if (checkHC5(soltmatrix, i, a, j)) {
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
        // Assign Jadwal Weekdays (0-4)
        for (int i = 0; i < 42; i++) {
            for (int j = 0; j < 15; j++) {
                for (int a = 0; a < 6; a++) {
                    // Mengecek HC
                    if (checkHC2(soltmatrix, i, a, j)) {
                        if (checkHC4Com(a, j)) {
                            if (checkHC4Weekend(i, j)) {
                                if (checkHC7(soltmatrix, i, a, j)) {
                                    if (checkHC5(soltmatrix, i, a, j)) {
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


