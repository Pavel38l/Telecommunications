package ru.vsu.telecom.data.util;

import com.opencsv.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Pavel Burdyug
 * Simple library for working with files
 */
public class FileUtils {
    /**
     * Read csv into a list of string arrays, each line is an string array, in which words are separated by separators
     * Ignores first line
     * @param csvFilePath the path to the file
     * @return list of string arrays, each line is an string array, in which words are separated by separators
     * @throws IOException if an I/O error occurs opening the file
     */
    public static List<String[]> readCsv(String csvFilePath) throws IOException {
        Reader reader = new BufferedReader(new FileReader(csvFilePath));
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .withCSVParser(parser)
                .build();
        List<String[]> dataList = csvReader.readAll();
        reader.close();
        csvReader.close();
        return dataList;
    }

    /**
     * Writes a list of arrays of strings to a csv file
     * @param csvFilePath the path to the file
     * @param stringArray list of string arrays
     * @throws IOException if an I/O error occurs opening the file
     */
    public static void write2Csv(String csvFilePath, List<String[]> stringArray) throws IOException {
        CSVWriter writer = new CSVWriter(
                new FileWriter(csvFilePath),
                ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END
        );
        writer.writeAll(stringArray);
        writer.close();
    }
}
