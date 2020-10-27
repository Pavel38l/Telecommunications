package ru.vsu.telecom.data.entity;

import lombok.Data;

/**
 * @author Pavel_Burdyug
 */

@Data
public class MobileConnectContract extends Contract {
    private int numberOfMinutes;
    private int numberOfSms;
    /**
     * Traffic size is measured in gigabytes
     */
    private double trafficSize;
}
