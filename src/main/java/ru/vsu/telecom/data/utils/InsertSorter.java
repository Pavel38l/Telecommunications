package ru.vsu.telecom.data.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Sorts an array using the insertion method according to the rules specified by the comparator
 * @author Pavel_Burdyug
 * @param <T> the type of elements in sortable array
 */
public class InsertSorter<T> implements Sorter<T> {

    @Override
    public T[] sort(Comparator<T> comparator, T[] array) {
        array = Arrays.copyOf(array, array.length);
        for (int i = 1;i < array.length;i++) {
            int j = i;
            while (j > 0 && comparator.compare(array[j], array[j - 1]) < 0) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = array[i];
        }
        return array;
    }
}
