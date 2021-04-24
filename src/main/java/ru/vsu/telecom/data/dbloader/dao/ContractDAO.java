package ru.vsu.telecom.data.dbloader.dao;

import ru.vsu.telecom.data.entity.Contract;

import java.util.List;
import java.util.Optional;

/**
 * @author Burdyug Pavel
 */
public class ContractDAO implements DAO<Contract> {
    @Override
    public Optional<Contract> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<Contract> getAll() {
        return null;
    }

    @Override
    public void create(Contract model) {

    }
}
