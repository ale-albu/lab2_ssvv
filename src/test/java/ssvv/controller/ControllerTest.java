package ssvv.controller;

import org.junit.Test;
import ssvv.model.CustomException;
import ssvv.model.Student;
import ssvv.repository.StudentRepository;

import static org.junit.Assert.*;

/**
 * Created by Alexandra-Ioana on 3/23/2018.
 */
public class ControllerTest {
    private StudentRepository repository;
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

}