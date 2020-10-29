package ru.vsu.telecom;

import ru.vsu.telecom.data.entity.*;
import ru.vsu.telecom.data.repository.ArrayContractRepository;
import ru.vsu.telecom.data.repository.ContractRepository;
import ru.vsu.telecom.data.util.Sorter;
import ru.vsu.telecom.factory.ObjectFactory;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pavel_Burdyug
 */
public class Main {
    public static void main(String[] args) {
        Sorter sorter = ObjectFactory.getInstance().createObject(Sorter.class);
    }
}
