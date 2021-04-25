package ru.vsu.telecom.data.dbloader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.repository.ArrayContractRepository;
import ru.vsu.telecom.data.repository.ContractRepository;
import ru.vsu.telecom.data.repository.SortFilterContractRepository;
import ru.vsu.telecom.data.utils.SetUpUtils;
import ru.vsu.telecom.factory.ObjectFactory;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Burdyug Pavel
 */
public class XmlContractLoaderTest {
    private SortFilterContractRepository contractRepository;
    private List<Contract> contracts = new ArrayList<>();
    private XmlContractLoader contractLoader;

    @Before
    public void setUp() {
        contractRepository = ObjectFactory.getInstance().createObject(ArrayContractRepository.class);
        SetUpUtils.generateRandomArrayContractRepository(100, contractRepository, contracts);
        contractLoader = ObjectFactory.getInstance().createObject(XmlContractLoader.class);
    }

    @Test
    public void saveAndLoad() {
        try {
            contractLoader.save(contractRepository);
            ContractRepository newRepository = contractLoader.load();
            Assert.assertEquals(contractRepository.getAll(), newRepository.getAll());
        } catch (JAXBException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}