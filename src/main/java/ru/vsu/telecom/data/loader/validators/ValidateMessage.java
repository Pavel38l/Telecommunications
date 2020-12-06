package ru.vsu.telecom.data.loader.validators;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Pavel Burdyug
 * Resulting validation message, contains the state and message
 */
@Getter
@Setter
public class ValidateMessage {
    private ValidateState state;
    private String message;

    public ValidateMessage() {
        this.state = ValidateState.OK;
    }

    public ValidateMessage(String message) {
        this.state = ValidateState.OK;
        this.message = message;
    }
}
