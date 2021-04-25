package ru.vsu.telecom.data.utils;

import org.apache.commons.lang3.RandomStringUtils;
import ru.vsu.telecom.data.entity.*;
import ru.vsu.telecom.data.repository.ArrayContractRepository;
import ru.vsu.telecom.data.repository.ContractRepository;
import ru.vsu.telecom.data.repository.SortFilterContractRepository;
import ru.vsu.telecom.factory.ObjectFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utils to set up Contract Repository for tests
 * @author Burdyug Pavel
 */
public class SetUpUtils {
    private static Random rnd = new Random();
    private static List<String> fullNames;

    public static void generateRandomArrayContractRepository(int contractsCount,
                                                             SortFilterContractRepository contractRepository,
                                                             List<Contract> contracts
    ) {
        fullNames = getFullNames();
        contractRepository.clear();
        for (int i = 0; i < contractsCount; i++) {
            Contract contract = createRandomContract(i);
            contractRepository.add(contract);
            contracts.add(contract);
        }
    }

    private static List<String> getFullNames() {
        List<String> fullNames = new ArrayList<>();
        String path = ClassLoader.getSystemClassLoader().getResource("fullname.txt").getPath();
        try (Scanner sc = new Scanner(new File(path), "UTF-8")) {
            while(sc.hasNext()) {
                fullNames.add(sc.nextLine().trim());
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return fullNames;
    }

    private static LocalDate createRandomLocalDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    private static ChannelPackage createRandomChannelPackage() {
        Long id = (long)rnd.nextInt(10);
        return ChannelPackage.builder()
                .id(id)
                .name(String.format("name-%d", id)/*RandomStringUtils.random(10, true, false)*/)
                .description(String.format("description-%d", id)/*RandomStringUtils.random(20, true, false)*/)
                .build();
    }

    private static Customer createRandomCustomer() {
        long id = rnd.nextInt(100);
        String fullName = fullNames.get(Math.abs(fullNames.size() - (int)id) - 1);
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);///createRandomLocalDate();
        Sex sex = Sex.values()[(int)id % 2];
        int passportSeriesNumber = 100000 + (int)id;
        return new Customer(
                id,
                fullName,
                dateOfBirth,
                sex,
                passportSeriesNumber
        );
    }
    public static Contract createRandomContract(int i) {
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
