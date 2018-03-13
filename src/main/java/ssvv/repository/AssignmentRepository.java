package ssvv.repository;

import ssvv.model.Assignment;
import ssvv.model.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Alexandra-Ioana on 3/9/2018.
 */
public class AssignmentRepository extends FileDataPersistence<Assignment> {
    private String file;

    public AssignmentRepository(String file) {
        super(file);
        this.file = file;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(file);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));
                if(items.size() == 5) {
                    int id = Integer.valueOf(items.get(0));
                    int regNumber = Integer.valueOf(items.get(1));
                    int pbNumber = Integer.valueOf(items.get(2));
                    float grade = Float.parseFloat(items.get(4));
                    Date date;
                    try {
                        date = format.parse(items.get(3));
                        Assignment assignment = new Assignment(id, regNumber, pbNumber, date, grade);
                        entities.put(id, assignment);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int getValidAssignmentId() {
        int maxId = entities.keySet().stream().mapToInt(Integer::intValue).max().getAsInt();
        return maxId + 1;
    }

    public boolean addGrade(int studentRegNumber, int labProblemNumber, float grade) {
        Optional<Assignment> assignmentOptional = findAssignment(studentRegNumber, labProblemNumber);
        if(!assignmentOptional.isPresent())
            return false;
        Assignment assignment = assignmentOptional.get();
        assignment.setGrade(grade);
        entities.put(assignment.getId(), assignment);
        saveAllToFile();
        return true;
    }

    private Optional<Assignment> findAssignment(int studentRegNumber, int labProblemNumber) {
        return entities.values().stream()
                .filter(a->a.getStudentRegistrationNumber() == studentRegNumber && a.getProblemNumber()==labProblemNumber)
                .findFirst();
    }

    private void saveAllToFile() {
        Path path = Paths.get(file);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.WRITE)) {
            for(Assignment a: entities.values()) {
                bufferedWriter.write(a.toString());
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Assignment> findAll() {
        return entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }
}
