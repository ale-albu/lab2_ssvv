package ssvv.validator;

import ssvv.model.Assignment;
import ssvv.model.CustomException;
import ssvv.model.LabProblem;
import ssvv.model.Student;

import java.util.Date;

public class Validator {

    public static void validateStudent(Student student) throws CustomException {
        if(student.getRegNumber() <= 0) {
            throw new CustomException("Registration number should be > 0");
        }
        if(!student.getName().matches("[a-zA-Z]+[\\s]?[a-zA-Z]+")){
            throw new CustomException("Invalid name.");
        }
        if(student.getGroup() > 900 || student.getGroup() < 100) {
            throw new CustomException("Invalid group. Should be between 100 and 900.");
        }
//        return student.getRegNumber() > 0 && student.getName().matches("[a-zA-Z]+[\\s]?[a-zA-Z]+")
//                && !(student.getGroup() > 900 || student.getGroup() < 100);
    }

    public static boolean validateLaboratory(LabProblem labProblem) {
        return labProblem.getLabProblemNumber() >= 1;
    }

    public static void validateAssignment(Assignment assignment) throws CustomException {
        validateGrade(assignment.getGrade());
        if(assignment.getProblemNumber() < 1)
            throw new CustomException("Problem number should be >=1");
        if(assignment.getStudentRegistrationNumber() <= 0)
            throw new CustomException("Student registration number should be > 0");
//        return assignment.getProblemNumber() >= 1 && validateGrade(assignment.getGrade())
//                && assignment.getStudentRegistrationNumber() > 0;
    }

    public static void validateGrade(float grade) throws CustomException {
        if(grade < 1 || grade > 10)
            throw new CustomException("Invalis grade. Should be between 1 and 10");
//        return grade >= 1 && grade <= 10;
    }
} 