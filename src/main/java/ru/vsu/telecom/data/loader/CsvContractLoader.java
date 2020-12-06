package ru.vsu.telecom.data.loader;

import ru.vsu.telecom.data.repository.ContractRepository;

import java.io.IOException;

/**
 * @author Pavel Burdyug
 * Interface for loading ContractRepository from csv-file and load CotractRepository to csv-file
 */

public interface CsvContractLoader {
    /**
     * Fills the repository with the contracts specified in the file
     * @param csvFilePath the path to the csv file
     * @param contractRepository repository for filling
     * @throws IOException if an I/O error occurs opening the file
     * @throws LoadException if csv data is not valid
     */
    void buildFromCsv(ContractRepository contractRepository, String csvFilePath) throws IOException, LoadException;

    /**
     * Writes repository contracts to the file
     * @param csvFilePath the path to the csv file
     * @param contractRepository repository to write to file
     * @throws IOException if an I/O error occurs opening the file
     * @throws RuntimeException if the data is incorrect and an error occurs when parsing
     */
    void writeToCsv(ContractRepository contractRepository, String csvFilePath) throws IOException, RuntimeException;
}
