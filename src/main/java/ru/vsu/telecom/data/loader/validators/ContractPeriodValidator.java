package ru.vsu.telecom.data.loader.validators;

import ru.vsu.telecom.data.entity.Contract;

import java.time.Period;

/**
 * @author Pavel Burdyug
 */
public class ContractPeriodValidator implements Validator {
    private static final int warningContractPeriod = 50;

    @Override
    public ValidateMessage validate(Contract contract) {
        ValidateMessage message = new ValidateMessage("Contract period is correct.");
        if (contract.getStartDate().compareTo(contract.getEndDate()) <= 0) {
            message.setState(ValidateState.ERROR);
            message.setMessage("Start date is later then end date!");
        }

        if (Period.between(contract.getStartDate(), contract.getEndDate()).getYears() >= warningContractPeriod) {
            message.setState(ValidateState.WARNING);
            message.setMessage(String.format("The contract has a term of more than %d years.", warningContractPeriod));
        }
        return message;
    }
}
