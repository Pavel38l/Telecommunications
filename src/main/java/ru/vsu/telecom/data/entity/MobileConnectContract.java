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
public class MobileConnectContract extends Contract {
    private int numberOfMinutes;
    private int numberOfSms;
    /**
     * Traffic size is measured in gigabytes
     */
    private double trafficSize;

    @Builder
    public MobileConnectContract(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber,
                                 Customer customer, int numberOfMinutes, int numberOfSms, double trafficSize) {
        super(id, startDate, endDate, contractNumber, customer);
        this.numberOfMinutes = numberOfMinutes;
        this.numberOfSms = numberOfSms;
        this.trafficSize = trafficSize;
    }

    public MobileConnectContract(LocalDate startDate, LocalDate endDate, Long contractNumber,
                                 Customer customer, int numberOfMinutes, int numberOfSms, double trafficSize) {
        super(startDate, endDate, contractNumber, customer);
        this.numberOfMinutes = numberOfMinutes;
        this.numberOfSms = numberOfSms;
        this.trafficSize = trafficSize;
    }
}
