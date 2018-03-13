package ssvv.repository;

import ssvv.model.CustomException;
import ssvv.model.LabProblem;
import ssvv.model.Student;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class FileDataPersistence<T> implements Repository<T> {
    private String file;
    Map<Integer, T> entities;

    public FileDataPersistence(String file) {
        this.file = file;
        entities = new HashMap<>();
    }

    @Override
    public void save(int id, T entity) throws CustomException {
        if(entities.containsKey(id))
            throw new CustomException("Duplicate entity.");
        entities.put(id, entity);
        try {
            BufferedWriter writer;
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(entity.toString() + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<T> findAll() {
        return entities.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    @Override
    public Optional<T> findOne(int id) {
        return Optional.ofNullable(entities.get(id));
    }
} 