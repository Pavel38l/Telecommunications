package ru.vsu.telecom.data.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Pavel_Burdyug
 */
public class BubbleSorterTest extends SorterTest {
    @Override
    public void setUp() {
        super.setUp();
        sorter = new BubbleSorter<>();
    }

    @Override
    @Test
    public void sort() {
        super.sort();
    }
}