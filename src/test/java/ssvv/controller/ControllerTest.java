package ssvv.controller;

import org.junit.Test;
import ssvv.model.Assignment;
import ssvv.model.CustomException;
import ssvv.model.LabProblem;
import ssvv.model.Student;
import ssvv.repository.AssignmentRepository;
import ssvv.repository.LabProblemRepository;
import ssvv.repository.StudentRepository;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Alexandra-Ioana on 3/23/2018.
 */
public class ControllerTest {
    private StudentRepository studentRepository;
    private AssignmentRepository assignmentRepository;
    private LabProblemRepository problemRepository;
    private Controller ctrl;

    private void deleteContentOfFile(String filename) {
        try {
            new PrintWriter(filename).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void testSuccessful(Student student) throws CustomException {
        String filename = "src/test/java/ssvv/students.txt";
        deleteContentOfFile(filename);
        studentRepository = new StudentRepository(filename);
        int nr = studentRepository.findAll().size();
        ctrl = new Controller(studentRepository, null, null);
        ctrl.saveStudent(student);

        assertEquals(nr+1, studentRepository.findAll().size());
    }

    private void testUnsuccessful(Student student) {
        String filename = "src/test/java/ssvv/students.txt";
        studentRepository = new StudentRepository(filename);
        int nr = studentRepository.findAll().size();
        ctrl = new Controller(studentRepository, null, null);
        try {
            ctrl.saveStudent(student);
            assertTrue(false);
        } catch (CustomException ex) {
            assertEquals(nr, studentRepository.findAll().size());
        }
    }

    @Test
    public void saveStudentRegNr1() throws Exception {
        Student student = new Student(1, "a", 123);
        testSuccessful(student);
    }

    @Test
    public void saveStudentRegNr2() throws Exception {
        Student student = new Student(0, "a", 123);
        testUnsuccessful(student);
    }

    @Test
    public void saveStudentGroup1() throws Exception {
        Student student = new Student(7, "a", 100);
        testSuccessful(student);
    }

    @Test
    public void saveStudentGroup2() throws Exception {
        Student student = new Student(2, "a", 101);
        testSuccessful(student);
    }

    @Test
    public void saveStudentGroup3() throws Exception {
        Student student = new Student(3, "a", 99);
        testUnsuccessful(student);
    }

    @Test
    public void saveStudentGroup4() throws Exception {
        Student student = new Student(4, "a", 900);
        testSuccessful(student);
    }

    @Test
    public void saveStudentGroup5() throws Exception {
        Student student = new Student(5, "a", 899);
        testSuccessful(student);
    }

    @Test
    public void saveStudentGroup6() throws Exception {
        Student student = new Student(6, "a", 901);
        testUnsuccessful(student);
    }

    private void addTestStudent() throws CustomException {
        ctrl.saveStudent(new Student(1, "a", 100));
    }

    private void addTestProblem() throws CustomException {
        ctrl.saveProblem(new LabProblem(1, "some text", 1));
    }

    private void deleteContentAllFilesAndInit() {
        String studentsFile = "src/test/java/ssvv/students.txt", problemsFile = "src/test/java/ssvv/laboratories.txt",
                assignmentsFile = "src/test/java/ssvv/assignments.txt";
        deleteContentOfFile(studentsFile);
        deleteContentOfFile(problemsFile);
        deleteContentOfFile(assignmentsFile);
        studentRepository = new StudentRepository(studentsFile);
        assignmentRepository = new AssignmentRepository(assignmentsFile);
        problemRepository = new LabProblemRepository(problemsFile);
        ctrl = new Controller(studentRepository, problemRepository, assignmentRepository);
    }

    private void testAssignmentSuccessful(Assignment assignment) throws CustomException {
        deleteContentAllFilesAndInit();
        addTestStudent();
        addTestProblem();
        int nr = assignmentRepository.findAll().size();

        try {
            ctrl.saveAssignment(assignment);
            assertEquals(nr + 1, assignmentRepository.findAll().size());
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    private void testAssignmentUnSuccessful(Assignment assignment) throws CustomException {
        int nr = assignmentRepository.findAll().size();
        try {
            ctrl.saveAssignment(assignment);
            assertTrue(false);
        } catch (CustomException e) {
            assertEquals(nr, assignmentRepository.findAll().size());
        }
    }


    @Test
    public void saveAssignmentValid() throws Exception {
        Assignment assignment = new Assignment(1, 1, new Date());
        deleteContentAllFilesAndInit();
        testAssignmentSuccessful(assignment);
    }

    @Test
    public void saveAssignmentInvalid() throws Exception {
        Assignment assignment = new Assignment(-1, 1, new Date());
        deleteContentAllFilesAndInit();
        // invalid assignment
        testAssignmentUnSuccessful(assignment);
    }

    @Test
    public void saveAssignmentInexistentStudent() throws Exception {
        Assignment assignment = new Assignment(2, 1, new Date());
        deleteContentAllFilesAndInit();
        addTestProblem();
        testAssignmentUnSuccessful(assignment);
    }

    @Test
    public void saveAssignmentInexistentProblem() throws Exception {
        Assignment assignment = new Assignment(1, 2, new Date());
        deleteContentAllFilesAndInit();
        addTestStudent();
        testAssignmentUnSuccessful(assignment);
    }

}