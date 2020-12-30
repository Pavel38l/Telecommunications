package ru.vsu.telecom.data.repository;

import ru.vsu.telecom.data.entity.*;
import ru.vsu.telecom.data.util.QuickSorter;
import ru.vsu.telecom.data.util.Sorter;
import ru.vsu.telecom.factory.InjectByType;
import ru.vsu.telecom.factory.ObjectFactory;

import java.util.*;
import java.util.function.Predicate;

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
    @InjectByType
    private Sorter<Contract> sorter;

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
    public SortFilterContractRepository filter(Predicate<Contract> contractPredicate) {
        SortFilterContractRepository repository = ObjectFactory.getInstance().createObject(ArrayContractRepository.class);
        for (int i = 0;i < count;i++) {
            if (contractPredicate.test(contractsArray[i])) {
                repository.add(contractsArray[i]);
            }
        }
        return repository;
    }

    @Override
    public SortFilterContractRepository sort(Comparator<Contract> contractComparator) {
        SortFilterContractRepository repository = ObjectFactory.getInstance().createObject(ArrayContractRepository.class);
        repository.addAll(
                Arrays.asList(
                        sorter.sort(contractComparator, getAll().toArray(new Contract[0]))
                )
        );
        return repository;
    }

    @Override
    public Optional<Contract> findOne(Predicate<Contract> contractPredicate) {
        for (int i = 0;i < count;i++) {
            if (contractPredicate.test(contractsArray[i])) {
                return Optional.of(contractsArray[i]);
            }
        }
        return Optional.empty();
    }

    @Override
    public void clear() {
        contractsArray = new Contract[INITIAL_SIZE];
        count = 0;
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
