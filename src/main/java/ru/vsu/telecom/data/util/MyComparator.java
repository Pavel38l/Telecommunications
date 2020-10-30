package ru.vsu.telecom.data.util;


/**
 * A comparison function used to compare objects
 * @author Pavel_Burdyug
 * @param <T> the type of objects to compare
 */
@FunctionalInterface
public interface MyComparator<T> {
    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.
     * @param o1 first object to compare
     * @param o2 second object to compare
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     * @throws NullPointerException – if an argument is null and this comparator does not permit null arguments
     */
    int compare(T o1, T o2);

    /**
     * Returns the composition of comparators
     * @param other other Comparator
     * @return the composition of comparators
     * @throws NullPointerException - if other is null
     */
    default MyComparator<T> thenComparing(MyComparator<T> other) {
        if (other == null) {
            throw new NullPointerException();
        }
        return (o1, o2) -> {
            int cmp = compare(o1, o2);
            return (cmp != 0) ? cmp : other.compare(o1, o2);
        };
    }
}


