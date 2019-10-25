package material.ordereddictionary;

/**
 * An interface for a dictionary storing (key-value) pairs.
 *
 * @author Michael Goodrich
 */
public interface OrderedDictionary<K, V> {

    /**
     * Returns the number of entries in the dictionary.
     * @return The size
     */
    int size();

    /**
     * Returns whether the dictionary is empty.
     * @return true if empty
     */
    boolean isEmpty();

    /**
     * Returns an entry containing the given key, or <tt>null</tt> if no such
     * entry exists.
     * @param key to search
     * @return The entry
     */
    Entry<K, V> find(K key);

    /**
     * Returns an iterator containing all the entries containing the given key,
     * or an empty iterator if no such entries exist.
     * @param key The key to search for
     * @return An iterable of entries
     */
    Iterable<Entry<K, V>> findAll(K key);

    /**
     * Inserts an item into the dictionary. Returns the newly created entry.
     * @param key
     * @param value
     * @return 
     */
    Entry<K, V> insert(K key, V value);

    /**
     * Removes and returns the given entry from the dictionary.
     * @param e
     */
    void remove(Entry<K, V> e);

    /**
     * Find range in ordered dictionaries.
     * @param minkey
     * @param maxkey
     * @return An iterable of entries
     */
    Iterable<Entry<K, V>> findRange(K minkey, K maxkey);
}
