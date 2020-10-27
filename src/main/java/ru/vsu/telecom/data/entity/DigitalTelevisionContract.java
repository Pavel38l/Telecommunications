package ru.vsu.telecom.data.entity;

import lombok.Data;

/**
 * @author Pavel_Burdyug
 */

@Data
public class DigitalTelevisionContract extends Contract {
    private ChannelPackage channelPackage;
}
