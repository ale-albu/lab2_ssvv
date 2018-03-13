package ssvv.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alexandra-Ioana on 3/5/2018.
 */
public class Assignment {
    private int id;
    private int studentRegistrationNumber;
    private int problemNumber;
    private Date date;
    private float grade;

    public Assignment(int studentRegistrationNumber, int problemNumber, Date date) {
        this.studentRegistrationNumber = studentRegistrationNumber;
        this.problemNumber = problemNumber;
        this.date = date;
        this.grade = 1;
    }

    public Assignment(int studentRegistrationNumber, int problemNumber, Date date, float grade) {
        this.studentRegistrationNumber = studentRegistrationNumber;
        this.problemNumber = problemNumber;
        this.date = date;
        this.grade = grade;
    }

    public Assignment(int id, int studentRegistrationNumber, int problemNumber, Date date, float grade) {
        this.id = id;
        this.studentRegistrationNumber = studentRegistrationNumber;
        this.problemNumber = problemNumber;
        this.date = date;
        this.grade = grade;
    }

    public int getStudentRegistrationNumber() {
        return studentRegistrationNumber;
    }

    public void setStudentRegistrationNumber(int studentRegistrationNumber) {
        this.studentRegistrationNumber = studentRegistrationNumber;
    }

    public int getProblemNumber() {
        return problemNumber;
    }

    public void setProblemNumber(int problemNumber) {
        this.problemNumber = problemNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return this.id + "," + this.studentRegistrationNumber + "," + this.problemNumber
                + "," + sdf.format(this.date) + "," + this.grade;
    }
}
