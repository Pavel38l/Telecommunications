package ru.vsu.telecom.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Pavel_Burdyug
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Contract {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long contractNumber;
    private Customer customer;

    public Contract(LocalDate startDate, LocalDate endDate, Long contractNumber, Customer customer) {
        this(null, startDate, endDate, contractNumber, customer);
    }
}
