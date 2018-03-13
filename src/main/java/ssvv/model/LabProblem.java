package ssvv.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LabProblem {
    private int labProblemNumber;
    private String text;
    private int labNumber;

    public LabProblem(int labProblemNumber, String text, int labNumber) {
        this.labProblemNumber = labProblemNumber;
        this.text = text;
        this.labNumber= labNumber;
    }

    public int getLabProblemNumber() {
        return labProblemNumber;
    }

    public void setLabProblemNumber(int labProblemNumber) {
        this.labProblemNumber = labProblemNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "LabProblem{" +
                "labProblemNumber=" + labProblemNumber +
                ", text='" + text + '\'' +
                '}';
    }
}