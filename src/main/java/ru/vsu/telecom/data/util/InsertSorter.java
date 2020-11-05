package ru.vsu.telecom.data.util;

import java.util.Arrays;
import java.util.Comparator;

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
            T x = array[i];
            while (j > 0 && comparator.compare(array[j - 1], x) > 0) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = x;
        }
        return array;
    }
}
