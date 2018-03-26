package ssvv.repository;

import ssvv.model.LabProblem;
import ssvv.model.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alexandra-Ioana on 3/7/2018.
 */
public class LabProblemRepository extends FileDataPersistence<LabProblem> {
    private String file;

    public LabProblemRepository(String file) {
        super(file);
        this.file = file;
        loadData();
    }

    @Override
    public void save(int id, LabProblem labProblem) {

    }

    private void loadData() {
        Path path = Paths.get(file);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));
                if(items.size() == 3) {
                    int nr = Integer.valueOf(items.get(0));
                    String text = items.get((1));
                    int labNr = Integer.valueOf(items.get(2));

                    LabProblem problem = new LabProblem(nr, text, labNr);
                    entities.put(nr, problem);
                }

            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
