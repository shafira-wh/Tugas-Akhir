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


    public static void main(String[] args) throws IOException, ParseException {


        // Read sheet1 Employee
        String[][] read = readFile().clone();
        employees = new Employee[read.length];
        for (int e = 0; e < read.length; e++) {
            employees[e] = new Employee(
                    Integer.parseInt(read[e][0]),
                    Double.parseDouble(read[e][1]),
                    (read[e][2].split(",")),
                    (read[e][3]));
        }

             for (int i = 0; i < employees.length; i++) {
                 System.out.println(
                         employees[i].getId() + " " +
                                 employees[i].getWorkHour() + " " +
                                 employees[i].getWorkWeekend()[0] + " " +
                                 employees[i].getWorkWeekend()[1] + " " +
                                 employees[i].getCompetence() + " ");

             }

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
                times [t][0] = startShift;
                times [t][1] = endShift;

            }
//        for (int s = 0; s < read1.length; s++) {
//            shifts[s] = new Shift(
//                    Integer.parseInt(read1[s][0]),
//                    Double.parseDouble(read1[s][1]), Double.parseDouble(read1[s][2]),
//                    Double.parseDouble(read1[s][3]), Double.parseDouble(read1[s][4]),
//                    Double.parseDouble(read1[s][5]), Double.parseDouble(read1[s][6]),
//                    Double.parseDouble(read1[s][7]), Integer.parseInt(read1[s][8]),
//                    (read1[s][9]), (times[s][0]), (times[s][1]), (read1[s][14]));
//        }


        for (int i = 0; i < shifts.length; i++) {
           System.out.println(
                    shifts[i].getIdShift() + " " +
                            shifts[i].getMon() + " " +
                            shifts[i].getTue() + " " +
                            shifts[i].getWed() + " " +
                            shifts[i].getThur() + " " +
                            shifts[i].getFri() + " " +
                            shifts[i].getSat() + " " +
                            shifts[i].getSun() + " " +
                            shifts[i].getShiftCat() + " " +
                            shifts[i].getShiftName() + " " +
                            shifts[i].getStartTime() +" "+
                            shifts[i].getEndTime() + " " +
                            shifts[i].getCompetence() + " ");
        }


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


// Read sheet4 Constraint (tipe String)
//        String[][] read3 = readFile3().clone();
//        Constraint[] constraints = new Constraint[read3.length];
//        for (int c = 0; c < read3.length; c++) {
//            constraints[c] = new Constraint(
//                    (read3[c][0]),
//                    (read3[c][1]),
//                    (read3[c][2]),
//                    (read3[c][3]));
//
//        }
//
//        for (int i = 0; i < constraints.length; i++) {
//            System.out.println(constraints[i].getHardC1() + " " +
//                    constraints[i].getHardC2() + " " +
//                    constraints[i].getHardC3() + " " +
//                    constraints[i].getHardC4() + " ");
        initsol();
    }

    // Membuat Matriks
    public static void initsol() {
        int[][] isiEmp = new int[15][42];
        for (int i = 0; i < 42; i++) {
            for (int j = 0; j < 15; j++) {
                for (int a = 0; a < 6; a++) {
                    //Mengecek HC
                    if (checkHC2(isiEmp, i, a, j)) {
                        if (checkHC4Com(a, j)) {
                            isiEmp[j][i] = a + 1;
                            break;
                        }
                    }


                }
            }
        }
        for (int i = 0; i < isiEmp.length; i++) {
            for (int j = 0; j < isiEmp[i].length; j++) {
                System.out.print(isiEmp[i][j] + " ");
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
                //if(checkHC4Com(shift, employee))
                return true;
        if (day % 7 == 3)
            if (needs(solution, shift, day) < manpowers[shift].getManThur())
                //if(checkHC4Com(shift, employee))
                return true;
        if (day % 7 == 4)
            if (needs(solution, shift, day) < manpowers[shift].getManFri())
                //if(checkHC4Com(shift, employee))
                return true;
        if (day % 7 == 5)
            if (needs(solution, shift, day) < manpowers[shift].getManSat())
                //if(checkHC4Com(shift, employee))
                return true;
        if (day % 7 == 6)
            if (needs(solution, shift, day) < manpowers[shift].getManSun())
                //if(checkHC4Com(shift, employee))
                return true;
        return false;
    }

    public static boolean checkHC4Com(int shift, int employee) {
        if (shifts[shift].getCompetence().equals("A") && employees[employee].getCompetence().equals("")) {
            return false;
        }
        return true;
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
            Iterator <Cell> cellIterator = row.cellIterator();

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

    @NotNull
    public static String[][] readFile3() throws IOException {
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

 }






