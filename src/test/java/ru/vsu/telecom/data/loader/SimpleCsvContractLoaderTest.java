package ru.vsu.telecom.data.loader;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.vsu.telecom.data.entity.*;
import ru.vsu.telecom.data.repository.ArrayContractRepository;
import ru.vsu.telecom.data.repository.ArrayContractRepositoryTest;
import ru.vsu.telecom.data.repository.SortFilterContractRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

/**
 * @author Pavel Burdyug
 */
public class SimpleCsvContractLoaderTest {
    private SortFilterContractRepository contractRepository;
    private SimpleCsvContractLoader csvContractLoader;
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
        csvContractLoader = new SimpleCsvContractLoader();
        contractRepository = new ArrayContractRepository();
        for (int i = 0; i < CONTRACT_COUNT; i++) {
            Contract contract = createRandomContract(i);
            contractRepository.add(contract);
            contracts.add(contract);
        }
    }

    @Test
    public void buildFromCsvAndWriteToCsv() {
        System.out.println("Valid load example: ");
        try {
            csvContractLoader.writeToCsv(contractRepository, "files/testData.csv");
            contractRepository.clear();
            try {
                csvContractLoader.buildFromCsv(contractRepository, "files/testData.csv");
            } catch (LoadException ex) {
                System.out.println(ex.getMessage());
            }

            Assert.assertEquals(contracts, contractRepository.getAll());
        } catch (IOException e) {
            System.out.println("File not found!");
        }
    }

    @Test(expected = LoadException.class)
    public void buildFromCsvErrorTest() {
        System.out.println("Load error example: ");
        if (contractRepository.get(0L).isPresent()) {
            contractRepository.get(0L).get().setId(-1L);
        }
        try {
            csvContractLoader.writeToCsv(contractRepository, "files/testData.csv");
            contractRepository.clear();
            csvContractLoader.buildFromCsv(contractRepository, "files/testData.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LocalDate createRandomLocalDate() {
        long minDay = LocalDate.of(1941, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2000, 12, 31).toEpochDay();
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
        LocalDate startDate = createRandomLocalDate();
        LocalDate endDate = startDate.plusYears(rnd.nextInt(100) + 1);
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