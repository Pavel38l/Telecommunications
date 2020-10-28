package ru.vsu.telecom.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

/**
 * @author Pavel_Burdyug
 */

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DigitalTelevisionContract extends Contract {
    private ChannelPackage channelPackage;

    public DigitalTelevisionContract(Long id, LocalDate startDate, LocalDate endDate, Long contractNumber,
                                     Customer customer, ChannelPackage channelPackage) {
        super(id, startDate, endDate, contractNumber, customer);
        this.channelPackage = channelPackage;
    }

    public DigitalTelevisionContract(LocalDate startDate, LocalDate endDate, Long contractNumber,
                                     Customer customer, ChannelPackage channelPackage) {
        super(startDate, endDate, contractNumber, customer);
        this.channelPackage = channelPackage;
    }
}
