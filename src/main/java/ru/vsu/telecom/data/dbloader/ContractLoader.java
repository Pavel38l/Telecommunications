package ru.vsu.telecom.data.dbloader;

import ru.vsu.telecom.data.repository.ContractRepository;

/**
 * @author Burdyug Pavel
 */
public interface ContractLoader {

    void save(ContractRepository contractRepository);

    void load(ContractRepository contractRepository);

}
