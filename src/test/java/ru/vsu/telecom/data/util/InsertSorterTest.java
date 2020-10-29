package ru.vsu.telecom.data.util;

import org.junit.Test;

/**
 * @author Pavel_Burdyug
 */
public class InsertSorterTest extends SorterTest {
    @Override
    public void setUp() {
        super.setUp();
        sorter = new InsertSorter<>();
    }

    @Override
    @Test
    public void sort() {
        super.sort();
    }
}