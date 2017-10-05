package com.ms.util;

import static java.util.Arrays.stream;

import java.util.Objects;
import java.util.function.Function;

/**
 * Util's to Compares Objects and properties
 *
 */
public interface EqualsHashCodeUtil {

    /**
     * @param thisz
     * @param that
     * @param propertyAccessors
     * @return boolean
     */
	@SafeVarargs
	static <T> boolean equals(T thisz, Object that, Function<T, ?>... propertyAccessors) {
        if (thisz == that) {
            return true;
        }
        if (thisz == null || that == null || thisz.getClass() != that.getClass()) {
            return false;
        }
        @SuppressWarnings("unchecked")
        final T other = (T) that;
        return stream(propertyAccessors)
                .allMatch(accessor -> Objects.deepEquals(accessor.apply(thisz), accessor.apply(other)));
    }
	
	/**
	 * @param values
	 * @return hashCode
	 */
	static int hashCode(Object... values) {
        return Objects.hash(values);
    }
}
