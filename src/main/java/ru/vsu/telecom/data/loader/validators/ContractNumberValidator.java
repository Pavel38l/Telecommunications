package ru.vsu.telecom.data.loader.validators;

import ru.vsu.telecom.data.entity.Contract;

/**
 * @author Pavel Burdyug
 * Сontract number validator
 */
public class ContractNumberValidator implements Validator {
    @Override
    public ValidateMessage validate(Contract contract) {
        ValidateMessage message = new ValidateMessage("Contract number is ok");
        if (contract.getContractNumber() <= 0) {
            message.setState(ValidateState.ERROR);
            message.setMessage("Negative contract number!");
        }
        return message;

    }
}
