package ru.vsu.telecom.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.telecom.data.dbloader.xml.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
    @XmlAttribute
    private Long id;
    private String fullName;
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
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
