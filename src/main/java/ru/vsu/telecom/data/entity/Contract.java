package ru.vsu.telecom.data.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Pavel_Burdyug
 */

@Data
public abstract class Contract {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long contractNumber;
    private Customer customer;
}
