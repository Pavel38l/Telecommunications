package ru.vsu.telecom.data.loader.validators;

import ru.vsu.telecom.data.entity.Contract;

/**
 * @author Pavel Burdyug
 */
public class CustomerAgeValidator implements Validator {
    private static final int minAge = 18;
    private static final int maxAge = 115;

    @Override
    public ValidateMessage validate(Contract contract) {
        ValidateMessage message = new ValidateMessage("Customer age is okay");
        if (contract.getCustomer().calcAge() < minAge) {
            message.setState(ValidateState.ERROR);
            message.setMessage(String.format("Customer age is less then %d!", minAge));
        }

        if (contract.getCustomer().calcAge() > maxAge) {
            message.setState(ValidateState.WARNING);
            message.setMessage(String.format("Customer age over %d!", maxAge));
        }
        return message;
    }
}
