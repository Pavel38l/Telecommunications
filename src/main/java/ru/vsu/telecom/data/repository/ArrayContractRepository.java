package ru.vsu.telecom.data.repository;

import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.util.Comparator;
import ru.vsu.telecom.data.util.Predicate;
import ru.vsu.telecom.data.util.Sorter;
import ru.vsu.telecom.factory.ObjectFactory;

import java.util.*;

/**
 * Implementation using an array
 * @author Pavel_Burdyug
 */
public class ArrayContractRepository implements SortFilterContractRepository {
    private static final int INITIAL_SIZE = 50;
    private Contract[] contractsArray = new Contract[INITIAL_SIZE];
    /**
     * Number of contracts
     */
    private int count = 0;
    private Sorter<Contract> sorter = ObjectFactory.getInstance().createObject(Sorter.class);


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
        if (contracts == null) {
            return false;
        }
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
            if (contractsArray[i].getId().equals(id)) {
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
            if (contractsArray[i].getId().equals(id)) {
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

    @Override
    public List<Contract> filter(Predicate<Contract> contractPredicate) {
        List<Contract> filteredList = new ArrayList<>();
        for (int i = 0;i < count;i++) {
            if (contractPredicate.predict(contractsArray[i])) {
                filteredList.add(contractsArray[i]);
            }
        }
        return filteredList;
    }

    @Override
    public List<Contract> sort(Comparator<Contract> contractComparator) {
        return Arrays.asList(sorter.sort(contractComparator, getAll().toArray(new Contract[0])));
    }

    /**
     * Array expansion - used when space is tight
     */
    private void arrayExpansion() {
        contractsArray = Arrays.copyOf(contractsArray, 2*contractsArray.length);
    }

    /**
     *  Shifts array elements that are to the right of the deleteIndex to the left by 1 positions
     * @param deleteIndex the index of the item to be deleted
     */
    private void arrayShift(int deleteIndex) {
        if (deleteIndex < 0 || deleteIndex >= count) {
            return;
        }

        for (int i = deleteIndex;i < count - 1;i++) {
            contractsArray[i] = contractsArray[i + 1];
        }
        contractsArray[count - 1] = null;
    }
}