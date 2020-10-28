package ru.vsu.telecom.data.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.vsu.telecom.data.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Pavel_Burdyug
 */
public class ArrayContractRepositoryTest {

    private ContractRepository contractRepository;
    private static final int CONTRACT_COUNT = 150;
    private Random rnd = new Random();
    private List<Contract> contracts = new ArrayList<>();
    @Before
    public void setUp() {
        contractRepository = new ArrayContractRepository();
        for (int i = 0; i < CONTRACT_COUNT; i++) {
            Contract contract;
            switch (i % 3) {
                case 0:
                    contract = new DigitalTelevisionContract();
                    break;
                case 1:
                    contract = new MobileConnectContract();
                    break;
                default:
                    contract = new WiredInternetContract();
                    break;
            }
            contract.setId((long) i);
            contractRepository.add(contract);
            contracts.add(contract);
        }
    }

    @Test
    public void getAll() {
        List<Contract> expected = contracts;
        Assert.assertNotNull(contractRepository.getAll());
        Assert.assertEquals(expected, contractRepository.getAll());
    }

    @Test
    public void add() {
        Assert.assertFalse(contractRepository.add(null));
        Contract[] contractsEx = {
                new DigitalTelevisionContract(),
                new MobileConnectContract(),
                new WiredInternetContract()
        };
        Contract contract = contractsEx[rnd.nextInt(contractsEx.length)];
        contract.setId((long)CONTRACT_COUNT + 1);
        contractRepository.add(contract);
        contracts.add(contract);
        Assert.assertEquals(contracts, contractRepository.getAll());
    }

    @Test
    public void addAll() {
        Assert.assertFalse(contractRepository.addAll(null));
        contractRepository.addAll(contracts);
        contracts.addAll(contracts);
        Assert.assertEquals(contracts, contractRepository.getAll());
    }

    @Test
    public void get() {
        for(int i = 0; i < CONTRACT_COUNT; i++) {
            Contract expected = contracts.get(i);
            Assert.assertTrue(contractRepository.get((long)i).isPresent());
            Contract actual = contractRepository.get((long)i).get();
            Assert.assertEquals(expected, actual);
        }
        Assert.assertTrue(contractRepository.get((long)CONTRACT_COUNT + 1).isEmpty());
    }

    @Test
    public void remove() {
        for (int i = 0;i < CONTRACT_COUNT;i++) {
            int j = rnd.nextInt(contracts.size());
            Contract c = contracts.remove(j);
            contractRepository.remove(c.getId());
            Assert.assertEquals(contracts, contractRepository.getAll());
        }

        boolean res = contractRepository.remove((long)CONTRACT_COUNT + 1);
        Assert.assertEquals(contracts.size(), contractRepository.count());
        Assert.assertFalse(res);

    }

    @Test
    public void count() {
        Assert.assertEquals(CONTRACT_COUNT, contractRepository.count());
    }
}