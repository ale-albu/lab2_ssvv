package ssvv.repository;

import ssvv.model.CustomException;

import java.util.List;
import java.util.Optional;

/**
 * Created by Alexandra-Ioana on 3/5/2018.
 */
public interface Repository<T> {
    void save(int id, T entity) throws CustomException;

    List<T> findAll();

    Optional<T> findOne(int id);
}
