package ru.vsu.telecom.data.parser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ru.vsu.telecom.data.entity.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser for contract
 * @author Pavel Burdyug
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractParser {
    DateTimeFormatter dataFormatter;
    private String separator;

    /**
     * Returns a contract created from a csv line
     * @param line array of string - line in csv file
     * @return a contract created from a csv line
     * @throws RuntimeException if the data is incorrect and an error occurs when parsing
     */

    public Contract contractFromCsvLine(String[] line) throws RuntimeException {
        Contract contract;
        for (int i = 0;i < line.length;i++) {
            line[i] = line[i].trim();
        }
        Long contractId = Long.parseLong(line[0]);
        LocalDate startDate = LocalDate.parse(line[1], dataFormatter);
        LocalDate endDate = LocalDate.parse(line[2], dataFormatter);
        Long contractNumber = Long.parseLong(line[3]);
        Customer customer = Customer.builder()
                .id(Long.parseLong(line[4]))
                .fullName(line[5])
                .dateOfBirth(LocalDate.parse(line[6], dataFormatter))
                .sex(Sex.valueOf(line[7].toUpperCase()))
                .passportSeriesNumber(Integer.parseInt(line[8]))
                .build();
        String type = line[9];
        String[] fields = line[10].split(String.format("[\\s%s]+", separator));
        switch (type.toUpperCase()) {
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


    /**
     * Returns an array of strings representing the contract to write to the csv file
     * @param contract contract
     * @return array of strings representing the contract to write to the csv file
     */
    public String[] contractToCsvLine(Contract contract) {
        List<String> strings = new ArrayList<>();
        strings.add(contract.getId().toString());
        strings.add(dataFormatter.format(contract.getStartDate()));
        strings.add(dataFormatter.format(contract.getEndDate()));
        strings.add(contract.getContractNumber().toString());
        Customer customer = contract.getCustomer();
        strings.add(customer.getId().toString());
        strings.add(customer.getFullName());
        strings.add(dataFormatter.format(customer.getDateOfBirth()));
        strings.add(customer.getSex().toString());
        strings.add(Integer.toString(customer.getPassportSeriesNumber()));
        List<String> addInfo = new ArrayList<>();
        if (contract.getClass() == MobileConnectContract.class) {
            strings.add("MOBILE");
            MobileConnectContract mCC = (MobileConnectContract) contract;
            addInfo.add(Integer.toString(mCC.getNumberOfMinutes()));
            addInfo.add(Integer.toString(mCC.getNumberOfSms()));
            addInfo.add(Double.toString(mCC.getTrafficSize()));
        } else if (contract.getClass() == WiredInternetContract.class) {
            strings.add("INTERNET");
            WiredInternetContract wIC = (WiredInternetContract) contract;
            addInfo.add(Double.toString(wIC.getConnectionSpeed()));
        } else {
            strings.add("TELEVISION");
            DigitalTelevisionContract dTC = (DigitalTelevisionContract) contract;
            addInfo.add(dTC.getChannelPackage().getId().toString());
            addInfo.add(dTC.getChannelPackage().getName());
            addInfo.add(dTC.getChannelPackage().getDescription());
        }
        strings.add(String.join(separator, addInfo));
        return strings.toArray(new String[0]);
    }
}
