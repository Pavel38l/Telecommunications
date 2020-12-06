package ru.vsu.telecom.data.loader.validators;

import ru.vsu.telecom.data.entity.Contract;

/**
 * @author Pavel Burdyug
 * Validator to validate contract {@link Contract}
 */
public interface Validator {

    /**
     * Returns validate message for contract that contains validate status and message {@link ValidateMessage}
     * @param contract contract to validate
     * @return validate message that contains validate status and message {@link ValidateMessage}
     */
    ValidateMessage validate(Contract contract);

    /**
     * Returns true if the validator is applicable to the given contract
     * @param contract contract to validate
     * @return if the validator is applicable to the given contract
     */
    default boolean isAppliableFor(Contract contract) {
        return true;
    }
}
