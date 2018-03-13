package ssvv.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import com.sun.org.apache.xpath.internal.operations.Bool;
import ssvv.controller.Controller;
import ssvv.model.Assignment;
import ssvv.model.CustomException;
import ssvv.model.LabProblem;
import ssvv.model.Student;
import ssvv.repository.AssignmentRepository;
import ssvv.repository.LabProblemRepository;
import ssvv.repository.StudentRepository;

public class LaboratoriesUI {
	private Controller controller;

    public LaboratoriesUI(){
    }

    private int readInt(BufferedReader br, String promptMessage, String errorMessage) throws IOException {
        boolean done = false;
        int result = -1;
        String resultStr;
        while(!done) {
            try {
                System.out.print(promptMessage);
                resultStr = br.readLine();
                result = Integer.parseInt(resultStr);
                done = true;
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
        return result;
    }

    private float readFloat(BufferedReader br, String promptMessage, String errorMessage) throws IOException {
        boolean done = false;
        float result = -1;
        String resultStr;
        while(!done) {
            try {
                System.out.print(promptMessage);
                resultStr = br.readLine();
                result = Float.parseFloat(resultStr);
                done = true;
            } catch (NumberFormatException e) {
                System.out.println(errorMessage);
            }
        }
        return result;
    }

    private Date readDate(BufferedReader br, String promptMessage, String errorMessage)throws IOException {
        boolean done = false;
        Date result = new Date();
        String resultStr;
        while(!done) {
            try {
                System.out.print(promptMessage);
                resultStr = br.readLine();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                result = format.parse(resultStr);
                done = true;
            } catch (ParseException e) {
                System.out.println(errorMessage);
            }
        }
        return result;
    }


    public void run() throws IOException{
        System.out.println("Starting");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StudentRepository studentRepository = new StudentRepository("students.txt");
        LabProblemRepository problemRepository = new LabProblemRepository("laboratories.txt");
        AssignmentRepository assignmentRepository = new AssignmentRepository("assignments.txt");
        this.controller = new Controller(studentRepository, problemRepository, assignmentRepository);

        while(true){
            System.out.println(" 1) Add student \n 2) Assign lab problem to student \n 3) Add grade \n 4) Get passing students \n");

            String line = br.readLine();
            switch (line)
            {
                case "1":
                    String name;
                    int group, registrationNumber;
                    registrationNumber = readInt(br, "Registration number: ",
                            "Invalid registration number. Must be a natural number. Please try again.");
                    System.out.print("Name: ");
                    name = br.readLine();
                    group = readInt(br, "Group number: ",
                            "Invalid group. Must be a natural number. Please try again.");

                    Student student = new Student(registrationNumber, name, group);
                    try {
                        controller.saveStudent(student);
                        System.out.println("Student saved successfully.");
                    } catch (CustomException e) {
                        System.out.println(e.getMessage());
                    }
//                    Boolean success = controller.saveStudent(student);
//                    if (!success) {
//                        System.out.println("Invalid student");
//                    } else {
//                        System.out.println("Student persisted successfully.");
//                    }
                    break;

                case "2":
                    int problemNumber, studentRegNumber;
                    Date date;
                    problemNumber = readInt(br, "Lab problem number: ",
                            "Invalid lab number. Must be a natural number. Please try again.");

                    studentRegNumber = readInt(br, "Student registration number: ",
                            "Invalid registration number. Must be a natural number. Please try again.");
                    Assignment assignment;
                    date = readDate(br, "Date (dd/mm/yyyy): ",
                            "Invalid date. Please try again.");
                    assignment = new Assignment(studentRegNumber, problemNumber, date);
                    try {
                        controller.saveAssignment(assignment);
                        System.out.println("Assignment saved successfully");
                    } catch (CustomException e) {
                        System.out.println(e.getMessage());
                    }
//                    boolean success = controller.saveAssignment(assignment);
//                    if (!success) {
//                        System.out.println("Invalid assignment.");
//                    } else {
//                        System.out.println("Assignment saved successfully");
//                    }
                    break;

                case "3":
                    registrationNumber = readInt(br, "Student registration number: ",
                            "Invalid registration number. Must be a natural number. Please try again.");
                    problemNumber = readInt(br, "Lab problem number: ",
                            "Invalid lab number. Must be a natural number. Please try again.");

                    float grade = readFloat(br, "Grade: ",
                            "Invalid grade. Please try again.");


                    try {
                        controller.addGrade(registrationNumber, problemNumber, grade);
                        System.out.println("Grade successfully saved.");

                    } catch (NumberFormatException|IOException|ParseException e) {
                        System.out.println("Invalid grade");
                    } catch (CustomException e) {
                        e.printStackTrace();
                    }
                    break;

                case "4":
                    try {
                        List<Student> passingStudents = controller.passedStudents();
                        System.out.println("Passing students: ");
                        for(Student s : passingStudents) {
                            System.out.println("\t - " + s.toString());
                        }
                    } catch (ParseException e) {
                        System.out.println("Could not get passing students");
                    }
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");

            }

        }
    }
} 