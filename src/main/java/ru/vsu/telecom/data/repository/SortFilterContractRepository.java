package ru.vsu.telecom.data.repository;

import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.util.MyComparator;
import ru.vsu.telecom.data.util.MyPredicate;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Extension of {@link ContractRepository} to provide additional methods to filter and sort contracts
 * @author Pavel_Burdyug
 *
 */
public interface SortFilterContractRepository extends ContractRepository {
    /**
     * Returns the SortFilterContractRepository consisting of the contracts that match the given predicate
     * @param contractPredicate predicate to apply to each contract to determine if it should be included
     * @return the SortFilterContractRepository consisting of the contracts that match the given predicate
     */
    ContractRepository filter(Predicate<Contract> contractPredicate);

    /**
     * Return a SortFilterContractRepository of contracts
     * sorted according to the comparison rules set by the comparator
     * @param contractComparator used to compare Contracts
     * @return a SortFilterContractRepository of contracts
     * sorted according to the comparison rules set by the comparator
     */
    ContractRepository sort(Comparator<Contract> contractComparator);

}
