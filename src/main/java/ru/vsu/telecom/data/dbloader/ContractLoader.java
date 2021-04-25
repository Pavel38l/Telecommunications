package ru.vsu.telecom.data.dbloader;

import ru.vsu.telecom.data.repository.ContractRepository;

/**
 * Contract loader interface
 * @author Burdyug Pavel
 */
public interface ContractLoader {

    /**
     * Save contracts from contractRepository
     * @param contractRepository contractRepository with contracts
     */
    void save(ContractRepository contractRepository);

    /**
     * Load contracts to contactRepository (old values will be lost)
     * @param contractRepository contractRepository for getting contracts
     */
    void load(ContractRepository contractRepository);

}
