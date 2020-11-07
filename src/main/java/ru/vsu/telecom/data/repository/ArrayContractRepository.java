package ru.vsu.telecom.data.repository;

import ru.vsu.telecom.data.entity.*;
import ru.vsu.telecom.data.util.FileUtils;
import ru.vsu.telecom.data.util.Sorter;
import ru.vsu.telecom.factory.ObjectFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private final Sorter<Contract> sorter = ObjectFactory.getInstance().createObject(Sorter.class);


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
    public void buildFromCsv(String csvFilePath) throws IOException {
        List<String[]> stringData =  FileUtils.readCsv(csvFilePath);
        for (String[] line : stringData) {

        }

    }

    @Override
    public SortFilterContractRepository filter(Predicate<Contract> contractPredicate) {
        SortFilterContractRepository repository = new ArrayContractRepository();
        for (int i = 0;i < count;i++) {
            if (contractPredicate.test(contractsArray[i])) {
                repository.add(contractsArray[i]);
            }
        }
        return repository;
    }

    @Override
    public SortFilterContractRepository sort(Comparator<Contract> contractComparator) {
        SortFilterContractRepository repository = new ArrayContractRepository();
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

    /**
     * Returns a contract created from a csv line
     * @return a contract created from a csv line
     */
    private Contract contractFromString(String[] line) {
        Contract contract;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Long contractId = Long.parseLong(line[0]);
        LocalDate startDate = LocalDate.parse(line[1], formatter);
        LocalDate endDate = LocalDate.parse(line[2], formatter);
        Long contractNumber = Long.parseLong(line[3]);
        Customer customer = Customer.builder()
                .id(Long.parseLong(line[4]))
                .fullName(line[5].trim())
                .dateOfBirth(LocalDate.parse(line[6], formatter))
                .sex(Sex.valueOf(line[7].trim().toUpperCase()))
                .passportSeriesNumber(Integer.parseInt(line[8]))
                .build();
        String type = line[9];
        String[] fields = line[10].split(",");
        switch (type.trim().toUpperCase()) {
            case "MOBILE":
                contract = MobileConnectContract.builder()
                        .id(contractId)
                        .startDate(startDate)
                        .endDate(endDate)
                        .contractNumber(contractNumber)
                        .customer(customer)
                        .numberOfMinutes(Integer.parseInt(fields[0]))
                        .numberOfSms(Integer.parseInt(fields[1]))
                        .trafficSize(Double.parseDouble(fields[2]))
                        .build();
                break;
            case "INTERNET":
                contract = WiredInternetContract.builder()
                        .id(contractId)
                        .startDate(startDate)
                        .endDate(endDate)
                        .contractNumber(contractNumber)
                        .customer(customer)
                        .connectionSpeed(Double.parseDouble(fields[0]))
                        .build();
                break;
            case "TELEVISION":
                ChannelPackage channelPackage = ChannelPackage.builder()
                        .id(Long.parseLong(fields[0]))
                        .name(fields[1])
                        .description(fields[2])
                        .build();
                contract = DigitalTelevisionContract.builder()
                        .id(contractId)
                        .startDate(startDate)
                        .endDate(endDate)
                        .contractNumber(contractNumber)
                        .customer(customer)
                        .channelPackage(channelPackage)
                        .build();
                break;
            default:
                throw new NumberFormatException("There is no such type");
        }
        return contract;
    }
}
