package ru.vsu.telecom.data.repository;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.vsu.telecom.data.entity.*;
import ru.vsu.telecom.data.util.MyComparator;
import ru.vsu.telecom.data.util.MyPredicate;
import ru.vsu.telecom.factory.ObjectFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Pavel_Burdyug
 */
public class ArrayContractRepositoryTest {

    private SortFilterContractRepository contractRepository;
    private static final int CONTRACT_COUNT = 150;
    private Random rnd = new Random();
    private List<Contract> contracts = new ArrayList<>();
    private static List<String> fullNames = new ArrayList<>();

    @BeforeClass
    public static void init() {
        String path = ClassLoader.getSystemClassLoader().getResource("fullname.txt").getPath();
        try (Scanner sc = new Scanner(new File(path), "UTF-8")) {
            while(sc.hasNext()) {
                fullNames.add(sc.nextLine().trim());
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Before
    public void setUp() {
        contractRepository = ObjectFactory.getInstance().createObject(ArrayContractRepository.class);
        for (int i = 0; i < CONTRACT_COUNT; i++) {
            Contract contract = createRandomContract(i);
            contractRepository.add(contract);
            contracts.add(contract);
        }
    }

    @Test
    public void getAll() {
        List<Contract> expected = contracts;
        Assert.assertNotNull(contractRepository.getAll());
        Assert.assertEquals(expected, contractRepository.getAll());
    }

    @Test
    public void add() {
        Assert.assertFalse(contractRepository.add(null));
        Contract[] contractsEx = {
                new DigitalTelevisionContract(),
                new MobileConnectContract(),
                new WiredInternetContract()
        };
        Contract contract = contractsEx[rnd.nextInt(contractsEx.length)];
        contract.setId((long)CONTRACT_COUNT + 1);
        contractRepository.add(contract);
        contracts.add(contract);
        Assert.assertEquals(contracts, contractRepository.getAll());
    }

    @Test
    public void addAll() {
        Assert.assertFalse(contractRepository.addAll(null));
        contractRepository.addAll(contracts);
        contracts.addAll(contracts);
        Assert.assertEquals(contracts, contractRepository.getAll());
    }

    @Test
    public void get() {
        for(int i = 0; i < CONTRACT_COUNT; i++) {
            Contract expected = contracts.get(i);
            Assert.assertTrue(contractRepository.get((long)i).isPresent());
            Contract actual = contractRepository.get((long)i).get();
            Assert.assertEquals(expected, actual);
        }
        Assert.assertTrue(contractRepository.get((long)CONTRACT_COUNT + 1).isEmpty());
    }

    @Test
    public void remove() {
        for (int i = 0;i < CONTRACT_COUNT;i++) {
            int j = rnd.nextInt(contracts.size());
            Contract c = contracts.remove(j);
            contractRepository.remove(c.getId());
            Assert.assertEquals(contracts, contractRepository.getAll());
        }

        boolean res = contractRepository.remove((long)CONTRACT_COUNT + 1);
        Assert.assertEquals(contracts.size(), contractRepository.count());
        Assert.assertFalse(res);

    }

    @Test
    public void count() {
        Assert.assertEquals(CONTRACT_COUNT, contractRepository.count());
    }

    @Test
    public void clear() {
        contractRepository.clear();
        contracts.clear();
        Assert.assertEquals(contracts, contractRepository.getAll());
    }

    @Test
    public void filter() {
        // mobile contracts with SMS more than 50
        Predicate<Contract> mobileContractPredicate = contract -> contract.getClass().equals(MobileConnectContract.class);
        Predicate<Contract> smsPredicate = contract -> {
            MobileConnectContract mcc = (MobileConnectContract) contract;
            return mcc.getNumberOfSms() > 50;
        };
        List<Contract> res = contractRepository.filter(mobileContractPredicate.
                and(smsPredicate)).getAll();


        List<Contract> expected = contracts.
                stream().
                filter(mobileContractPredicate.
                and(smsPredicate)).
                collect(Collectors.toList());
        Assert.assertEquals(expected, res);
    }

    @Test
    public void findOne() {
        for (Contract contract : contracts) {
            Optional<Contract> actual = contractRepository.findOne(e -> e.getId().equals(contract.getId()));
            Assert.assertTrue(actual.isPresent());
            Assert.assertEquals(contract.getId(), actual.get().getId());
        }

    }

    @Test
    public void sort() {
        Comparator<Contract> age = Comparator.comparingInt(c -> c.getCustomer().calcAge());
        List<Contract> res = contractRepository.sort(
                age.thenComparingLong(Contract::getId)
        ).getAll();

        var expected = contracts.stream().sorted(age.thenComparingLong(Contract::getId)).collect(Collectors.toList());
        Assert.assertEquals(expected, res);
    }

    private LocalDate createRandomLocalDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    private ChannelPackage createRandomChannelPackage() {
        return ChannelPackage.builder()
                .id((long)rnd.nextInt(10))
                .name(RandomStringUtils.random(10, true, false))
                .description(RandomStringUtils.random(20, true, false))
                .build();
    }

    private Customer createRandomCustomer() {
        long id = rnd.nextInt(100);
        String fullName = fullNames.get(rnd.nextInt(fullNames.size()));
        LocalDate dateOfBirth = createRandomLocalDate();
        Sex sex = Sex.values()[rnd.nextInt(Sex.values().length)];
        int passportSeriesNumber = 100000 + rnd.nextInt(100000);
        return new Customer(
                id,
                fullName,
                dateOfBirth,
                sex,
                passportSeriesNumber
        );
    }
    public Contract createRandomContract(int i) {
        Contract contract;
        Long contractNumber = 100000L + rnd.nextInt(100000);
        LocalDate startDate = createRandomLocalDate(), endDate = createRandomLocalDate();
        Customer customer = createRandomCustomer();
        ChannelPackage channelPackage = createRandomChannelPackage();
        int numberOfMinutes = rnd.nextInt(300);
        int numberOfSms = rnd.nextInt(300);
        double trafficSize = rnd.nextInt(10);
        double connectionSpeed = rnd.nextInt(400);
        switch (i % 3) {
            case 0:
                contract = new DigitalTelevisionContract(
                        (long) i,
                        startDate,
                        endDate,
                        contractNumber,
                        customer,
                        channelPackage
                );
                break;
            case 1:
                contract = new MobileConnectContract(
                        (long) i,
                        startDate,
                        endDate,
                        contractNumber,
                        customer,
                        numberOfMinutes,
                        numberOfSms,
                        trafficSize
                );
                break;
            default:
                contract = new WiredInternetContract(
                        (long) i,
                        startDate,
                        endDate,
                        contractNumber,
                        customer,
                        connectionSpeed
                );
                break;
        }
        return contract;
    }
}