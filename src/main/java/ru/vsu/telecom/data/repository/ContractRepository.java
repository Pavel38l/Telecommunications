package ru.vsu.telecom.data.repository;

import ru.vsu.telecom.data.entity.Contract;

import java.util.List;
import java.util.Optional;

/**
 * @author Pavel_Burdyug
 */

/**
 * Repository for interacting with contracts
 */
public interface ContractRepository {
    /**
     * Return List of all contracts
     * @return List of all contracts
     */
    List<Contract> getAll();

    /**
     * Add contract to the repository.
     * @param contract contract to add
     * @return true if the addition was successful and false if it isn't
     */
    boolean add(Contract contract);

    /**
     * Add list of contracts to the repository.
     * @param contracts list of contracts to add
     * @return true if the addition was successful and false if it isn't
     */
    boolean addAll(List<Contract> contracts);

    /**
     * Gets a contract with a specific id from the repository
     * @param id contract unique id
     * @return an Optional describing the contract with a specific id, or an empty Optional if there is no contract with
     * such id in repository
     */
    Optional<Contract> get(Long id);

    /**
     * Remove the contract with a specific id from the repository
     * @param id id of the contract to be removed
     * @return true if the removing was successful and false if it isn't
     */
    boolean remove(Long id);

    /**
     * Return the number of contracts in repository
     * @return the number of contracts in repository
     */
    int count();
}
