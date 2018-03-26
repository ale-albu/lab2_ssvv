package ssvv.controller;

import javafx.util.Pair;
import ssvv.model.Assignment;
import ssvv.model.CustomException;
import ssvv.model.LabProblem;
import ssvv.model.Student;
import ssvv.repository.AssignmentRepository;
import ssvv.repository.LabProblemRepository;
import ssvv.repository.Repository;
import ssvv.repository.StudentRepository;
import ssvv.validator.Validator;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Controller {
    private Repository<Student> studentPersistence;
    private Repository<LabProblem> laboratoryPersistence;
    private AssignmentRepository assignmentPersistence;

    public Controller(Repository<Student> studentRepo, Repository<LabProblem> problemRepo, AssignmentRepository assignmentRepo) {
//        this.studentPersistence = new StudentRepository("students.txt");
//        this.laboratoryPersistence = new LabProblemRepository("laboratories.txt");
//        this.assignmentPersistence = new AssignmentRepository("assignments.txt");
        studentPersistence = studentRepo; laboratoryPersistence = problemRepo; assignmentPersistence = assignmentRepo;
    }

    public void saveStudent(Student student) throws CustomException {
        Validator.validateStudent(student);
        studentPersistence.save(student.getRegNumber(), student);
//        if (Validator.validateStudent(student)) {
//            this.studentPersistence.save(student.getRegNumber(), student);
//            return true;
//        } else {
//            return false;
//        }
    }

    public void saveAssignment(Assignment assignment) throws CustomException {
        if(Validator.validateAssignment(assignment)) {
            if(!studentPersistence.findOne(assignment.getStudentRegistrationNumber()).isPresent()) {
                throw new CustomException("There is no student with this registration number");
            }
            if(!laboratoryPersistence.findOne(assignment.getProblemNumber()).isPresent()) {
                throw new CustomException("There is no lab problem with this number");
            }
            int id = assignmentPersistence.getValidAssignmentId();
            assignment.setId(id);
            assignmentPersistence.save(id, assignment);
        }
    }

    public void addGrade(int studentRegNumber, int labNumber, float grade)
            throws NumberFormatException, IOException, ParseException, CustomException {
        Validator.validateGrade(grade);
        assignmentPersistence.addGrade(studentRegNumber, labNumber, grade);
//        return Validator.validateGrade(grade) && this.assignmentPersistence.addGrade(studentRegNumber, labNumber, grade);
    }

    public List<Student> passedStudents() throws NumberFormatException,
            IOException, ParseException {
        List<Assignment> assignments = assignmentPersistence.findAll();

        Map<Integer, Pair<Integer, Float>> studentGrades = new HashMap<>();

        assignments.forEach(a->{
            int studentId = a.getStudentRegistrationNumber();
            if(studentGrades.containsKey(studentId)) {
                int nrGrades = studentGrades.get(studentId).getKey();
                float sumOfGrades = studentGrades.get(studentId).getValue();
                studentGrades.put(studentId, new Pair<>(++nrGrades, sumOfGrades + a.getGrade()));
            } else {
                studentGrades.put(studentId, new Pair<>(1, a.getGrade()));
            }

        });

        List<Student> passedStudents = new ArrayList<>();

        studentGrades.forEach((studId, nrSum) -> {
            float average = nrSum.getValue() / nrSum.getKey();
            if(average >= 5) {
                passedStudents.add(studentPersistence.findOne(studId).get());
            }
        });

        return passedStudents;
    }
} 