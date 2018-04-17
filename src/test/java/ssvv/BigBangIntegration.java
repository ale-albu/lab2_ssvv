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

/**
 * Created by Alexandra-Ioana on 4/16/2018.
 */
public class BigBangIntegration {
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

    private void deleteContentAllFilesAndInit() {
        deleteContentOfFile(studentsFile);
        deleteContentOfFile(problemsFile);
        deleteContentOfFile(assignmentsFile);
        studentRepository = new StudentRepository(studentsFile);
        assignmentRepository = new AssignmentRepository(assignmentsFile);
        problemRepository = new LabProblemRepository(problemsFile);
        ctrl = new Controller(studentRepository, problemRepository, assignmentRepository);
    }

    @Test
    public void saveStudentTest() throws Exception {
        Student student = new Student(1, "a", 123);
        String filename = "src/test/java/ssvv/students.txt";
        deleteContentOfFile(filename);
        studentRepository = new StudentRepository(filename);
        int nr = studentRepository.findAll().size();
        ctrl = new Controller(studentRepository, null, null);
        ctrl.saveStudent(student);
        assertEquals(nr + 1, studentRepository.findAll().size());
    }

    @Test
    public void saveAssignmentAndGradeTest() throws Exception {
        Assignment assignment = new Assignment(1, 1, new Date());
        deleteContentAllFilesAndInit();
        int studRegNr = 1, pbNr = 1;
        ctrl.saveStudent(new Student(studRegNr, "a", 100));
        ctrl.saveProblem(new LabProblem(pbNr, "some text", 1));
        int nr = assignmentRepository.findAll().size();
        try {
            ctrl.saveAssignment(assignment);
            int repoSize = assignmentRepository.findAll().size();
            assertEquals(nr + 1, repoSize);
            ctrl.addGrade(studRegNr, pbNr, 5.5f);
            assertEquals(assignmentRepository.findAssignment(studRegNr, pbNr).get().getGrade(), 5.5f, 0.001);
        } catch (CustomException e) {
            assertTrue(false);
        }
    }

    @Test
    public void passedStudentsTest() throws Exception {
        deleteContentAllFilesAndInit();
        studentRepository.save(1, new Student(1, "a", 100));
        studentRepository.save(2, new Student(2, "b", 100));
        studentRepository.save(3, new Student(3, "c", 100));

        problemRepository.save(1, new LabProblem(1, "bla bla", 1));

        assignmentRepository.save(1, new Assignment(1, 1, new Date(), 7));
        assignmentRepository.save(2, new Assignment(1, 1, new Date(), 2));
        assignmentRepository.save(3, new Assignment(2, 1, new Date(), 5));
        assignmentRepository.save(4, new Assignment(1, 1, new Date(), 8));

        assertEquals(ctrl.passedStudents().size(), 2);
    }

}
