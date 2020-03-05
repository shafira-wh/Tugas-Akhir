package com.company;

public class Employee {
    public int idEmployee;
    public double workHour;
    public int [] workWeekend;
    public String competence;

    public Employee(int id_employee, double work_Hour, int [] work_Weekend, String e_competence) {
        idEmployee = id_employee;
        workHour = work_Hour;
        workWeekend = work_Weekend;
        competence = e_competence;

    }
    public int getId(){
        return idEmployee ;
    }

    public double getWorkHour() {
        return workHour;
    }

    public int [] getWorkWeekend() {
        return workWeekend;
    }
    public String getCompetence(){
        return competence;
    }
}
