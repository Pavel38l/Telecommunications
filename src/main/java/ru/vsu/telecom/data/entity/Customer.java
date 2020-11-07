package ru.vsu.telecom.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;


/**
 * @author Pavel_Burdyug
 */

@Builder
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return passportSeriesNumber == customer.passportSeriesNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(passportSeriesNumber);
    }
}
