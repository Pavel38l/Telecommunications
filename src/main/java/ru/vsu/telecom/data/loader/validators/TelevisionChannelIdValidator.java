package ru.vsu.telecom.data.loader.validators;

import org.apache.commons.lang3.builder.Diff;
import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.entity.DigitalTelevisionContract;

/**
 * @author Pavel Burdyug
 */
public class TelevisionChannelIdValidator implements Validator {
    @Override
    public ValidateMessage validate(Contract contract) {
        ValidateMessage message = new ValidateMessage("Television channel package id is ok.");
        DigitalTelevisionContract dt = (DigitalTelevisionContract) contract;
        if (dt.getChannelPackage().getId() < 0) {
            message.setState(ValidateState.ERROR);
            message.setMessage("Negative television package id!");
        }
        return message;
    }

    @Override
    public boolean isAppliableFor(Contract contract) {
        return contract instanceof DigitalTelevisionContract;
    }

}
