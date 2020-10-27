package ru.vsu.telecom.data.entity;

import lombok.Data;

/**
 * @author Pavel_Burdyug
 */

public enum Sex {
    MALE,
    FEMALE;

    @Override
    public String toString() {
        return String.format(this.name().toLowerCase());
    }
}
