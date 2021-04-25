package ru.vsu.telecom.data.dbloader.dao;

import java.util.List;
import java.util.Optional;

/**
 * Data access object interface to manage data
 * @author Burdyug Pavel
 */
public interface DAO<T> {
    /**
     * Return object by id
     * @param id object id
     * @return object by id
     */
    Optional<T> get(int id);

    /**
     * Return all objects
     * @return all objects
     */
    List<T> getAll();

    /**
     * Save object
     * @param model object to save
     */
    void save(T model);

    /**
     * Save list of objects
     * @param models list of objects to save
     */
    void saveAll(List<T> models);
}
