package material.ordereddictionary;

/**
 * Interface for a key-value pair entry
 *
 * @param <K>
 * @param <V>
 */
public interface Entry<K, V> {

    /**
     * Returns the key stored in this entry.
     *
     * @return The key
     */
    K getKey();

    /**
     * Returns the value stored in this entry.
     *
     * @return The value
     */
    V getValue();
}
