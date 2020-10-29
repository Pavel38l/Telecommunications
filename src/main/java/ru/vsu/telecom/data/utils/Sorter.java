package ru.vsu.telecom.data.utils;

import java.util.List;


/**
 * Returns a sorted array according to the rules specified by the comparator,
 * does not change the passed array
 * @author Pavel_Burdyug
 * @param <T> the type of elements in sortable array
 */
public interface Sorter<T> {

    /**
     * Sorts the array according to the rules given by the comparator
     * @param comparator the Comparator used to compare array elements
     * @param array an array to sort
     * @return sorted array
     */
    T[] sort(Comparator<T> comparator, T[] array);
}
