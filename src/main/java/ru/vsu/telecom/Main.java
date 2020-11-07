package ru.vsu.telecom;

import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.repository.ArrayContractRepository;
import ru.vsu.telecom.data.repository.ContractRepository;
import ru.vsu.telecom.data.util.FileUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Pavel_Burdyug
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ContractRepository contractRepository = new ArrayContractRepository();
        List<String[]> d = FileUtils.readCsv(ClassLoader.getSystemResource("data.csv").getPath());
        for (String[] l : d) {
            for (var w : l) {
                System.out.print(w + " ");
            }
            System.out.println();
        }
        contractRepository.buildFromCsv(ClassLoader.getSystemResource("dat.csv").getPath());
        Optional<Contract> contract = contractRepository.get(1L);
        System.out.println(contract.get());
        //contractRepository.writeToCsv("src/main/resources/dat.csv");
    }
}
