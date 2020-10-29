package ru.vsu.telecom.data.repository;

import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.util.Comparator;
import ru.vsu.telecom.data.util.Predicate;

import java.util.List;

/**
 * Extension of {@link ContractRepository} to provide additional methods to filter and sort contracts
 * @author Pavel_Burdyug
 *
 */
public interface SortFilterContractRepository extends ContractRepository {
    /**
     * Returns a list consisting of the contracts that match the given predicate
     * @param contractPredicate predicate to apply to each contract to determine if it should be included
     * @return the list consisting of the contracts that match the given predicate
     */
    List<Contract> filter(Predicate<Contract> contractPredicate);

    /**
     * Return a list of contracts
     * sorted according to the comparison rules set by the comparator
     * @param contractComparator used to compare Contracts
     * @return a list of contracts
     * sorted according to the comparison rules set by the comparator
     */
    List<Contract> sort(Comparator<Contract> contractComparator);
}
