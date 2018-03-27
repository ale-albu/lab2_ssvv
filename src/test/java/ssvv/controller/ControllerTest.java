package ssvv.controller;

import org.junit.Test;
import ssvv.model.Assignment;
import ssvv.model.CustomException;
import ssvv.model.Student;
import ssvv.repository.AssignmentRepository;
import ssvv.repository.LabProblemRepository;
import ssvv.repository.StudentRepository;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Alexandra-Ioana on 3/23/2018.
 */
public class ControllerTest {
    private StudentRepository repository;
    private AssignmentRepository assignmentRepository;
    private LabProblemRepository problemRepository;
    private Controller ctrl;

    private void testSuccessful(Student student) throws CustomException {
        repository = new StudentRepository("src/test/java/ssvv/students.txt");
        int nr = repository.findAll().size();
        ctrl = new Controller(repository, null, null);
        ctrl.saveStudent(student);

        assertEquals(nr+1, repository.findAll().size());
    }

    private void testUnsuccessful(Student student) {
        repository = new StudentRepository("src/test/java/ssvv/students.txt");
        int nr = repository.findAll().size();
        ctrl = new Controller(repository, null, null);
        try {
            ctrl.saveStudent(student);
            assertTrue(false);
        } catch (CustomException ex) {
            assertEquals(nr, repository.findAll().size());
        }
    }

    @Test
    public void saveStudentRegNr1() throws Exception {
        Student student = new Student(199, "a", 123);
        testSuccessful(student);
    }

    @Test
    public void saveStudentRegNr2() throws Exception {
        Student student = new Student(0, "a", 123);
        testUnsuccessful(student);
    }

    @Test
    public void saveStudentGroup1() throws Exception {
        Student student = new Student(709, "a", 100);
        testSuccessful(student);
    }

    @Test
    public void saveStudentGroup2() throws Exception {
        Student student = new Student(209, "a", 101);
        testSuccessful(student);
    }

    @Test
    public void saveStudentGroup3() throws Exception {
        Student student = new Student(309, "a", 99);
        testUnsuccessful(student);
    }

    @Test
    public void saveStudentGroup4() throws Exception {
        Student student = new Student(409, "a", 900);
        testSuccessful(student);
    }

    @Test
    public void saveStudentGroup5() throws Exception {
        Student student = new Student(509, "a", 899);
        testSuccessful(student);
    }

    @Test
    public void saveStudentGroup6() throws Exception {
        Student student = new Student(609, "a", 901);
        testUnsuccessful(student);
    }

    @Test
    public void saveAssignmentValid() throws Exception {
        Assignment assignment = new Assignment(111, 1, new Date());
        repository = new StudentRepository("src/test/java/ssvv/students.txt");
        assignmentRepository = new AssignmentRepository("src/test/java/ssvv/assignments.txt");
        problemRepository = new LabProblemRepository("src/test/java/ssvv/laboratories.txt");
        int nr = assignmentRepository.findAll().size();
        ctrl = new Controller(repository, problemRepository, assignmentRepository);

        try {
            ctrl.saveAssignment(assignment);
            assertTrue(true);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            assertTrue(false);
        }
    }

    @Test
    public void saveAssignmentInvalid() throws Exception {
        Assignment assignment = new Assignment(-1, 1, new Date());
        // invalid assignment
        repository = new StudentRepository("src/test/java/ssvv/students.txt");
        assignmentRepository = new AssignmentRepository("src/test/java/ssvv/assignments.txt");
        problemRepository = new LabProblemRepository("src/test/java/ssvv/laboratories.txt");
        ctrl = new Controller(repository, problemRepository, assignmentRepository);
        int nr = assignmentRepository.findAll().size();
        try {
            ctrl.saveAssignment(assignment);
            assertTrue(false);
        } catch (CustomException e) {
            assertTrue(true);
        }
    }

}