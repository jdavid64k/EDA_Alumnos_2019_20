package material.maps;

/**
 * Interface for a key-value pair entry
 *
 * @param <K> Key type
 * @param <V> value type
 * @author R. Cabido, A. Duarte, and J. Velez
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
