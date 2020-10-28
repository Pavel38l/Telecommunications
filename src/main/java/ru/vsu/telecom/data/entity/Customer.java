package ru.vsu.telecom.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;


/**
 * @author Pavel_Burdyug
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private Sex sex;
    private int passportSeriesNumber;

    public Customer(String fullName, LocalDate dateOfBirth, Sex sex, int passportSeriesNumber) {
        this(null, fullName, dateOfBirth, sex, passportSeriesNumber);
    }

    /**
     * Calculate customer age
     * @return customer age
     */
    public int calcAge() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dateOfBirth, currentDate).getYears();
    }
}
