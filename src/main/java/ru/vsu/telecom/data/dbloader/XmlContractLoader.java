package ru.vsu.telecom.data.dbloader;

import ru.vsu.telecom.data.entity.Contract;
import ru.vsu.telecom.data.repository.ArrayContractRepository;
import ru.vsu.telecom.data.repository.ContractRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Class to marshall and unmarshall ContractRepository
 * @author Burdyug Pavel
 */
public class XmlContractLoader {
    JAXBContext context;

    /**
     * Configure JAXBContext
     * @throws JAXBException
     */
    public XmlContractLoader() throws JAXBException {
        this.context = JAXBContext.newInstance(
                ArrayContractRepository.class,
                Contract.class
        );
    }

    /**
     * Marshall contractRepository to xml file (save in file)
     * @param contractRepository contract repository to save
     * @param filePath path to xml file
     */
    public void save(ContractRepository contractRepository, String filePath) {
        try {

            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(contractRepository, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * default save to files/repository.xml
     * @param contractRepository repository to save
     */
    public void save(ContractRepository contractRepository) {
        save(contractRepository, "files/repository.xml");
    }

    /**
     * Returns contractRepository from xml file (unmarshall contractRepository)
     * @param filePath path to xml file (where to download the repository from)
     * @return contractRepository from xml file (unmarshall contractRepository)
     * @throws JAXBException
     * @throws FileNotFoundException where filepath is not correct
     */
    public ContractRepository load(String filePath) throws JAXBException, FileNotFoundException {
        return (ContractRepository) context.createUnmarshaller().unmarshal(new FileReader(filePath));
    }

    public ContractRepository load() throws JAXBException, FileNotFoundException {
        return load("files/repository.xml");
    }
}
