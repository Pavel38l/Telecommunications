package ru.vsu.telecom.data.dbloader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.entity.CustomerTest;
import ru.vsu.telecom.data.repository.ArrayContractRepository;
import ru.vsu.telecom.data.repository.SortFilterContractRepository;
import ru.vsu.telecom.data.utils.SetUpUtils;
import ru.vsu.telecom.factory.ObjectFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Burdyug Pavel
 */
public class DbContractLoaderTest {
    private SortFilterContractRepository contractRepository;
    private List<Contract> contracts = new ArrayList<>();
    private ContractLoader contractLoader;

    @Before
    public void setUp() {
        contractRepository = ObjectFactory.getInstance().createObject(ArrayContractRepository.class);
        SetUpUtils.generateRandomArrayContractRepository(100, contractRepository, contracts);
        contractLoader = ObjectFactory.getInstance().createObject(DbContractLoader.class);
    }

    @Test
    public void save() {
        contractLoader.save(contractRepository);
        contractLoader.load(contractRepository);
        Assert.assertEquals(contracts, contractRepository.getAll());
    }

}