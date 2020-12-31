package ru.vsu.telecom.data.loader;

import ru.vsu.telecom.data.entity.ChannelPackage;
import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.entity.Customer;
import ru.vsu.telecom.data.entity.DigitalTelevisionContract;
import ru.vsu.telecom.data.loader.validators.*;
import ru.vsu.telecom.data.repository.ContractRepository;
import ru.vsu.telecom.data.util.FileUtils;
import ru.vsu.telecom.factory.InjectByType;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Burdyug
 */
public class SimpleCsvContractLoader implements CsvContractLoader {

    @InjectByType
    private static List<Validator> validators = new ArrayList<>();
  /*  static {
        validators.add(new IdValidator());
        validators.add(new StartDateValidator());
        validators.add(new EndDateValidator());
        validators.add(new ContractPeriodValidator());
        validators.add(new ContractNumberValidator());
        validators.add(new CustomerAgeValidator());
        validators.add(new TelevisionChannelIdValidator());
        validators.add(new MobileSmsValidator());
        validators.add(new InternetValidator());
    }*/

    private final ContractParser parser = ContractParser.builder()
            .dataFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            .separator(",")
            .build();

    @Override
    public void buildFromCsv(ContractRepository contractRepository,
                             String csvFilePath) throws IOException, LoadException {
        List<String[]> stringData =  FileUtils.readCsv(csvFilePath);
        List<Customer> customers = new ArrayList<>();
        List<ChannelPackage> channelPackages = new ArrayList<>();
        for (String[] line : stringData) {
            Contract contract = parser.contractFromCsvLine(line);
            // Contract validate
            List<ValidateMessage> messages = validateContract(contract);
            System.out.printf("Contract id=%d%n", contract.getId());
            messages.forEach(System.out::println);
            System.out.println();
            if (!messages.isEmpty() && messages.get(messages.size() - 1).getState() == ValidateState.ERROR) {
                throw new LoadException(messages.get(messages.size() - 1).getMessage());
            }

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

    /**
     * Validate contract with all validators
     * @param contract Contract to validate
     * @return list of result validate message
     */
    private List<ValidateMessage> validateContract(Contract contract) {
        List<ValidateMessage> messages = new ArrayList<>();
        for (Validator validator : validators) {
            if (validator.isAppliableFor(contract)) {
                ValidateMessage message = validator.validate(contract);
                messages.add(message);
                if (message.getState() == ValidateState.ERROR) {
                    break;
                }
            }
        }
        return messages;
    }
}
