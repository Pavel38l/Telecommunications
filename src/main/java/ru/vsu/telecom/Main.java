package ru.vsu.telecom;

import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.entity.MobileConnectContract;

/**
 * @author Pavel_Burdyug
 */
public class Main {
    public static void main(String[] args) {
        Contract c = new MobileConnectContract();
        System.out.println(c.getId());
    }
}
