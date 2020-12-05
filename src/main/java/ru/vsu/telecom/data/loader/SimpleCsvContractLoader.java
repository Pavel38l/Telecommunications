package ru.vsu.telecom.data.loader;

import ru.vsu.telecom.data.entity.ChannelPackage;
import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.entity.Customer;
import ru.vsu.telecom.data.entity.DigitalTelevisionContract;
import ru.vsu.telecom.data.parser.ContractParser;
import ru.vsu.telecom.data.repository.ContractRepository;
import ru.vsu.telecom.data.util.FileUtils;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Burdyug
 */
public class SimpleCsvContractLoader implements CsvContractLoader {

    private final ContractParser parser = ContractParser.builder()
            .dataFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            .separator(",")
            .build();
    @Override
    public void buildFromCsv(ContractRepository contractRepository,
                             String csvFilePath) throws IOException, RuntimeException {
        List<String[]> stringData =  FileUtils.readCsv(csvFilePath);
        List<Customer> customers = new ArrayList<>();
        List<ChannelPackage> channelPackages = new ArrayList<>();
        for (String[] line : stringData) {
            Contract contract = parser.contractFromCsvLine(line);
            int exIndex = customers.indexOf(contract.getCustomer());
            if (exIndex != -1) {
                contract.setCustomer(customers.get(exIndex));
            }
            if (contract.getClass() == DigitalTelevisionContract.class) {
                DigitalTelevisionContract dTC = (DigitalTelevisionContract) contract;
                exIndex = channelPackages.indexOf(dTC.getChannelPackage());
                if (exIndex != -1) {
                    dTC.setChannelPackage(channelPackages.get(exIndex));
                }
            }
            contractRepository.add(contract);
        }
    }

    @Override
    public void writeToCsv(ContractRepository contractRepository, String csvFilePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        data.add(
                new String[]{"CONTRACTID","START", "END",
                        "CONTRACTNUMBER", "CUSTOMERID", "FULLNAME",
                        "DATEOFBITRH", "SEX" , "PASSPORTSERIESNUMBER","TYPE", "ADDINFO"}
        );
        contractRepository.getAll().forEach(contract -> data.add(parser.contractToCsvLine(contract)));
        FileUtils.write2Csv(csvFilePath, data);
    }
}
