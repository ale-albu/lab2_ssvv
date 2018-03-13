package ssvv.repository;

import ssvv.model.LabProblem;

import java.util.List;

/**
 * Created by Alexandra-Ioana on 3/7/2018.
 */
public class LabProblemRepository extends FileDataPersistence<LabProblem> {
    private String file;

    public LabProblemRepository(String file) {
        super(file);
    }

    @Override
    public void save(int id, LabProblem labProblem) {

    }

}
