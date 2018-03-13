package ssvv.repository;

import ssvv.controller.Controller;
import ssvv.model.Student;

import static org.junit.Assert.*;

/**
 * Created by Alexandra-Ioana on 3/13/2018.
 */
public class StudentRepositoryTest {
    StudentRepository repository;
    Controller ctrl;

    @org.junit.Test
    public void findAll() throws Exception {
        repository = new StudentRepository("src/test/java/ssvv/students.txt");
        int nr = repository.findAll().size();
        ctrl = new Controller(repository, null, null);
        ctrl.saveStudent(new Student(1, "a b", 200));

        assertEquals(nr+1, repository.findAll().size());
    }

    @org.junit.Test
    public void findAllStudentsEmptyList() throws Exception {
        repository = new StudentRepository("src/test/java/ssvv/student.txt");
        int nr = repository.findAll().size();
        assertEquals(nr, 0);
    }

}