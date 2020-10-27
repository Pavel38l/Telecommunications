package ru.vsu.telecom.data.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.Period;


/**
 * @author Pavel_Burdyug
 */

@Data
public class Customer {
    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private Sex sex;
    private int passportSeriesNumber;

    /**
     * Calculate customer age
     * @return customer age
     */
    public int calcAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dateOfBirth, currentDate).getYears();
    }
}
