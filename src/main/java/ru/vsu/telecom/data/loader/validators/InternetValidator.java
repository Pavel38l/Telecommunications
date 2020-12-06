package ru.vsu.telecom.data.loader.validators;

import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.entity.WiredInternetContract;

/**
 * @author Pavel Burdyug
 */
public class InternetValidator implements Validator {
    @Override
    public ValidateMessage validate(Contract contract) {
        ValidateMessage message = new ValidateMessage("Internet connection speed is okay!");
        WiredInternetContract wc = (WiredInternetContract) contract;
        if (wc.getConnectionSpeed() <= 0) {
            message.setState(ValidateState.ERROR);
            message.setMessage("Connection speed must be positive!");
        }
        return message;
    }

    @Override
    public boolean isAppliableFor(Contract contract) {
        return contract instanceof WiredInternetContract;
    }

}
