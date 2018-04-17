package ssvv;

import org.junit.Test;
import ssvv.controller.Controller;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ssvv.BigBangTest.*;

/**
 * Created by Alexandra-Ioana on 4/17/2018.
 */
public class Incrementalntegration {
    private String studentsFile = "src/test/java/ssvv/students.txt",
            problemsFile = "src/test/java/ssvv/laboratories.txt",
            assignmentsFile = "src/test/java/ssvv/assignments.txt";

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

    private void init() {
        studentRepository = new StudentRepository(studentsFile);
        assignmentRepository = new AssignmentRepository(assignmentsFile);
        problemRepository = new LabProblemRepository(problemsFile);
        ctrl = new Controller(studentRepository, problemRepository, assignmentRepository);
    }


    @Test
    public void saveStudentTest() throws Exception {
        Student student = new Student(1, "a", 123);
        deleteContentOfFile(studentsFile);
        studentRepository = new StudentRepository(studentsFile);
        int nr = studentRepository.findAll().size();
        ctrl = new Controller(studentRepository, null, null);
        ctrl.saveStudent(student);
        assertEquals(nr + 1, studentRepository.findAll().size());
    }

    @Test
    public void saveStudentAssignmentAndGradeTest() throws Exception {
        Assignment assignment = new Assignment(1, 1, new Date());
        deleteContentOfFile(studentsFile);
        deleteContentOfFile(assignmentsFile);
        deleteContentOfFile(problemsFile);
        init();

        int studRegNr = 1, pbNr = 1;
        int nrStudents = studentRepository.findAll().size();
        ctrl.saveStudent(new Student(studRegNr, "a", 100));
        assertEquals(nrStudents + 1, studentRepository.findAll().size());
        ctrl.saveProblem(new LabProblem(pbNr, "some text", 1));
        int nr = assignmentRepository.findAll().size();

        ctrl.saveAssignment(assignment);
        int repoSize = assignmentRepository.findAll().size();
        assertEquals(nr + 1, repoSize);
        try {
            ctrl.addGrade(studRegNr, pbNr, -2);
            assertTrue(false);
        } catch (CustomException ex) {
            assertEquals(assignmentRepository.findAssignment(studRegNr, pbNr).get().getGrade(), 1, 0.001);
        }
    }

    @Test
    public void testIntegration() throws Exception {
        Student student1 = new Student(1, "a", 123),
                student2 = new Student(2, "b", 100);
        deleteContentOfFile(studentsFile);
        deleteContentOfFile(assignmentsFile);
        deleteContentOfFile(problemsFile);
        init();
        int nr = studentRepository.findAll().size();
        ctrl.saveStudent(student1);
        assertEquals(nr + 1, studentRepository.findAll().size());
        ctrl.saveStudent(student2);
        assertEquals(nr + 2, studentRepository.findAll().size());
        ctrl.saveProblem(new LabProblem(1, "some text", 1));

        ctrl.saveAssignment(new Assignment(1, 1, new Date(), 4));
        ctrl.saveAssignment(new Assignment(2, 1, new Date(), 10));
        ctrl.saveAssignment(new Assignment(1, 1, new Date(), 4.5f));

        assertEquals(ctrl.passedStudents().size(), 1);
    }
}
