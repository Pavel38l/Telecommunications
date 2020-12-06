package ru.vsu.telecom.data.loader.validators;

import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.entity.MobileConnectContract;

/**
 * @author Pavel Burdyug
 */
public class MobileSmsValidator implements Validator {
    @Override
    public ValidateMessage validate(Contract contract) {
        ValidateMessage message = new ValidateMessage("Sms count is okay.");
        MobileConnectContract mc = (MobileConnectContract) contract;
        if (mc.getNumberOfSms() < 0) {
            message.setState(ValidateState.ERROR);
            message.setMessage("Negative sms count!");
        }
        return message;
    }

    @Override
    public boolean isAppliableFor(Contract contract) {
        return contract instanceof MobileConnectContract;
    }

}
