package ru.vsu.telecom;

import lombok.SneakyThrows;
import ru.vsu.telecom.data.util.FileUtils;
import java.util.List;
import java.util.Scanner;

/**
 * @author Pavel_Burdyug
 */
public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        List<String[]> l = FileUtils.readCsv(ClassLoader.getSystemResource("pop.csv").getPath());
        for (String[] s : l) {
            for (String str : s) {
                System.out.print(str);
            }
            System.out.println();
        }
        FileUtils.write2Csv("pop.csv", l);
    }
}
