package ru.vsu.telecom.data.util;

import org.junit.Test;

/**
 * @author Pavel_Burdyug
 */
public class QuickSorterTest extends SorterTest {
    @Override
    public void setUp() {
        super.setUp();
        sorter = new QuickSorter<>();
    }

    @Override
    @Test
    public void sort() {
        super.sort();
    }
}