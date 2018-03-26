package ssvv.repository;

import ssvv.model.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Alexandra-Ioana on 3/7/2018.
 */
public class StudentRepository extends FileDataPersistence<Student> {
    private String file;

    public StudentRepository(String file) {
        super(file);
        this.file = file;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(file);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));
                if(items.size() == 3) {
                    int regNumber = Integer.valueOf(items.get(0));
                    String name = items.get((1));
                    int group = Integer.parseInt(items.get(2));

                    Student student = new Student(regNumber, name, group);
                    entities.put(regNumber, student);
                }

            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Student> findAll() {
        return entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

}
