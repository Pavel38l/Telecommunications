package ru.vsu.telecom.data.util;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Sorts an array using the bubble method according to the rules specified by the comparator
 * @author Pavel_Burdyug
 * @param <T> the type of elements in sortable array
 */
public class BubbleSorter<T> implements Sorter<T> {
    @Override
    public T[] sort(Comparator<T> comparator, T[] array) {
        array = Arrays.copyOf(array, array.length);
        for (int i = 0;i < array.length - 1;i++) {
            for (int j = array.length - 1;j > i;j--) {
                if (comparator.compare(array[j], array[j - 1]) < 0) {
                    var temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }
}
