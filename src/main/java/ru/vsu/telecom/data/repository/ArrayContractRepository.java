package ru.vsu.telecom.data.repository;

import ru.vsu.telecom.data.entity.Contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Pavel_Burdyug
 */

/**
 * Implementation using an array
 */
public class ArrayContractRepository implements ContractRepository {
    private static final int INITIAL_SIZE = 50;
    private Contract[] contractsArray = new Contract[INITIAL_SIZE];
    /**
     * Number of contracts
     */
    private int count = 0;


    @Override
    public List<Contract> getAll() {
        List<Contract> contractList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            contractList.add(contractsArray[i]);
        }
        return contractList;
    }

    @Override
    public boolean add(Contract contract) {
        if (contract == null) {
            return false;
        }
        if (count + 1 > contractsArray.length) {
            arrayExpansion();
        }
        contractsArray[count] = contract;
        count++;
        return true;
    }

    @Override
    public boolean addAll(List<Contract> contracts) {
        for (Contract contract : contracts) {
            if (!add(contract)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Optional<Contract> get(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        for (int i = 0;i < count;i++) {
            if (contractsArray[i].getId() == id) {
                return Optional.of(contractsArray[i]);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean remove(Long id) {
        if (id == null) {
            return false;
        }
        for (int i = 0;i < count;i++) {
            if (contractsArray[i].getId() == id) {
                arrayShift(i);
                count--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int count() {
        return count;
    }

    /**
     * Array expansion - used when space is tight
     */
    private void arrayExpansion() {
        Contract[] newContractsArray = new Contract[2*contractsArray.length];
        for (int i = 0;i < contractsArray.length;i++) {
            newContractsArray[i] = contractsArray[i];
        }
        contractsArray = newContractsArray;
    }

    /**
     *  Shifts array elements that are to the right of the deleteIndex to the left by 1 positions
     * @param deleteIndex the index of the item to be deleted
     */
    private void arrayShift(int deleteIndex) {
        if (deleteIndex < 0 || deleteIndex >= count) {
            return;
        }
        if (deleteIndex == count - 1) {
            contractsArray[deleteIndex] = null;
        }
        for (int i = deleteIndex;i < count - 1;i++) {
            contractsArray[i] = contractsArray[i + 1];
        }
    }
}
