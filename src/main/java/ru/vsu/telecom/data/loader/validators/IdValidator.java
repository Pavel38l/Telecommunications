package ru.vsu.telecom.data.loader.validators;

import org.apache.commons.lang3.Validate;
import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.entity.Customer;

import java.util.Set;

/**
 * @author Pavel Burdyug
 */
public class IdValidator implements Validator {
    @Override
    public ValidateMessage validate(Contract contract) {
        ValidateMessage message = new ValidateMessage("Contract id is okay.");
        if (contract.getId() < 0) {
            message.setState(ValidateState.ERROR);
            message.setMessage("Negative contract id!");
        }
        return message;
    }

}
