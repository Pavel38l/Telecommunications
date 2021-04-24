package ru.vsu.telecom.data.dbloader;

import lombok.SneakyThrows;
import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.repository.ContractRepository;

import java.lang.reflect.Field;

/**
 * @author Burdyug Pavel
 */
public class DbContractLoader implements ContractLoader {
    @SneakyThrows
    @Override
    public void save(ContractRepository contractRepository) {
        Contract contract = contractRepository.getAll().get(0);
        for (Field field : Contract.class.getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println(field.getType() + " " + field.getName() + " " + field.get(contract));
        }
    }

    @Override
    public void load(ContractRepository contractRepository) {

    }
}
