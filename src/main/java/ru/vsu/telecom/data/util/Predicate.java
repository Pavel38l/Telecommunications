package ru.vsu.telecom.data.util;


/**
 * Predicate - boolean-valued function of one argument.
 * @author Pavel_Burdyug
 * @param <T> - the type of argument
 */
@FunctionalInterface
public interface Predicate<T> {

    /**
     * Calculate the predicate
     * @param t - the input argument
     * @return true if the input argument matches the predicate, otherwise false
     */
    boolean predict(T t);

    /**
     * Returns the composition of predicates using a boolean operator and
     * @param other other predicate
     * @return the composition of predicates using a boolean operator and
     * @throws NullPointerException if other is null
     */
    default Predicate<T> or(Predicate<? super T> other) {
        if (other == null) {
            throw new NullPointerException();
        }
        return (t) -> predict(t) || other.predict(t);
    }

    /**
     * Returns the composition of predicates using a boolean operator or
     * @param other other predicate
     * @return the composition of predicates using a boolean operator or
     * @throws NullPointerException if other is null
     */
    default Predicate<T> and(Predicate<? super T> other) {
        if (other == null) {
            throw new NullPointerException();
        }
        return (t) -> predict(t) && other.predict(t);
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     * @return the predicate that represents the logical negation of this predicate
     */
    default Predicate<T> not() {
        return (t) ->!predict(t);
    }

}
