package ru.vsu.telecom.data.loader.validators;

import ru.vsu.telecom.data.entity.Contract;

import java.time.LocalDate;

/**
 * @author Pavel Burdyug
 */
public class EndDateValidator implements Validator{
    LocalDate minDate = LocalDate.of(1941, 1, 1);

    @Override
    public ValidateMessage validate(Contract contract) {
        ValidateMessage validateMessage = new ValidateMessage("Contract end date is ok.");
        if (contract.getStartDate().compareTo(minDate) < 0) {
            validateMessage.setState(ValidateState.ERROR);
            validateMessage.setMessage(String.format("Date can't be earlier then %s", minDate.toString()));
        }
        return validateMessage;
    }
}
