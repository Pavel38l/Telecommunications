package ru.vsu.telecom.data.util;

import java.util.Arrays;

/**
 * Sorts the array using one-pivot quick sort according to the rules specified by the comparator.
 * @author Pavel_Burdyug
 * @param <T> the type of elements in sortable array
 */
public class QuickSorter<T> implements Sorter<T> {

    @Override
    public T[] sort(Comparator<T> comparator, T[] array) {
        array = Arrays.copyOf(array, array.length);
        QuickSort(array, 0, array.length - 1, comparator);
        return array;
    }

    /**
     * Sorts a specific part of the array
     * @param array - array to sort
     * @param l - the left border of the sorted area
     * @param r - the right border of the sorted area
     * @param comparator - the Comparator used to compare array elements
     */
    private void QuickSort(T[] array, int l, int r, Comparator<T> comparator) {
        if (l < r) {
            int q = Partition(array, l, r, comparator);
            QuickSort(array, l, q - 1, comparator);
            QuickSort(array, q + 1, r, comparator);
        }
    }

    /**
     * Forms a 'pivot' element
     * @param array array to sort
     * @param l the left border of the sorted area
     * @param r the right border of the sorted area
     * @param comparator the Comparator used to compare array elements
     * @return index of 'pivot' element
     */
    private int Partition(T[] array, int l, int r, Comparator<T> comparator) {
        var x = array[r];
        int less = l;
        for (int i = l; i < r; ++i) {
            if (comparator.compare(array[i], x) <= 0) {
                var temp = array[i];
                array[i] = array[less];
                array[less] = temp;
                less++;
            }
        }
        var temp = array[less];
        array[less] = array[r];
        array[r] = temp;
        return less;
    }
}
