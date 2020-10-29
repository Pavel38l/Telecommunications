package ru.vsu.telecom.data.utils;

import java.util.Arrays;
import java.util.List;

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
            for (int j = i;j < array.length - 1;j++) {
                if (comparator.compare(array[j + 1], array[j]) < 0) {
                    var temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }
}
