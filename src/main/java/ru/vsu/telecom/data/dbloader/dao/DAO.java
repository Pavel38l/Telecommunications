package ru.vsu.telecom.data.dbloader.dao;

import java.util.List;
import java.util.Optional;

/**
 * @author Burdyug Pavel
 */
public interface DAO<T> {
    Optional<T> get(int id);
    List<T> getAll();
    void save(T model);
    void saveAll(List<T> models);
}
