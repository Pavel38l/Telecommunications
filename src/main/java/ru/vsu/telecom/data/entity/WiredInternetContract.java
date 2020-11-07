package ru.vsu.telecom.data.entity;

import lombok.*;

import java.time.LocalDate;

/**
 * @author Pavel_Burdyug
 */

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WiredInternetContract extends Contract {
    private double connectionSpeed;

    @Builder
    public WiredInternetContract(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber,
                                 Customer customer, double connectionSpeed) {
        super(id, startDate, endDate, contractNumber, customer);
        this.connectionSpeed = connectionSpeed;
    }

    public WiredInternetContract(LocalDate startDate, LocalDate endDate, Long contractNumber,
                                 Customer customer, double connectionSpeed) {
        super(startDate, endDate, contractNumber, customer);
        this.connectionSpeed = connectionSpeed;
    }


}
