package ru.vsu.telecom.data.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Pavel_Burdyug
 */
public abstract class SorterTest {

    protected static final int ELEMENTS_COUNT = 444;
    protected Random rnd = new Random();
    protected Sorter<Integer> sorter;
    protected Integer[] array;

    @Before
    public void setUp() {
        array = new Integer[ELEMENTS_COUNT];
        for (int i = 0;i < ELEMENTS_COUNT;i++) {
            array[i] = rnd.nextInt(ELEMENTS_COUNT);
        }
    }

    @Ignore
    @Test
    public void sort() {
        var expectedSortArray = Arrays.copyOf(array, array.length);
        Arrays.sort(expectedSortArray);
        var actualSortArray = sorter.sort(
                Integer::compare,
                array
        );
        for (int i = 0;i < ELEMENTS_COUNT;i++) {
            Assert.assertEquals(expectedSortArray[i], actualSortArray[i]);
        }
    }
}