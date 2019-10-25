package material.maps;

import java.util.Iterator;
import java.util.List;

/**
 * Separate chaining table implementation of hash tables. Note that all
 * "matching" is based on the equals method.
 *
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro, JD. Quintana
 */
public class HashTableMapSC<K, V> implements Map<K, V> {
    //TODO: Practica 4 Ejercicio 2

    private class HashEntry<T, U> implements Entry<T, U> {

        protected T key;
        protected U value;

        public HashEntry(T k, U v) {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public U getValue() {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public T getKey() {
            throw new RuntimeException("Not yet implemented.");
        }

        public U setValue(U val) {
            throw new RuntimeException("Not yet implemente.");
        }

        @Override
        public boolean equals(Object o) {
            throw new RuntimeException("Not yet implemented.");
        }
    }

    private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {

        public HashTableMapIterator(List<HashEntry<T, U>>[] map, int numElems) {
            throw new RuntimeException("Not yet implemented.");
        }


        @Override
        public boolean hasNext() {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public Entry<T, U> next() {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");

        }

    }

    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {

        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public T next() {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public boolean hasNext() {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {

        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public U next() { throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public boolean hasNext() {
            throw new RuntimeException("Not yet implemented.");
        }

        @Override
        public void remove() {
            //NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }



    /**
     * Creates a hash table with prime factor 109345121 and capacity 1000.
     */
    public HashTableMapSC() {
        throw new RuntimeException("Not yet implemented.");
    }

    /**
     * Creates a hash table with prime factor 109345121 and given capacity.
     *
     * @param cap initial capacity
     */
    public HashTableMapSC(int cap) {
        throw new RuntimeException("Not yet implemented.");
    }

    /**
     * Creates a hash table with the given prime factor and capacity.
     *
     * @param p   prime number
     * @param cap initial capacity
     */
    public HashTableMapSC(int p, int cap) {
        throw new RuntimeException("Not yet implemented.");
    }

    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return the hash value
     */
    protected int hashValue(K key) {
        throw new RuntimeException("Not yet implemented.");
    }


    @Override
    public int size(){ throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public boolean isEmpty() {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public V get(K key) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public V put(K key, V value) {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public V remove(K key) {
        throw new RuntimeException("Not yet implemented.");
    }


    @Override
    public Iterator<Entry<K, V>> iterator() {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public Iterable<K> keys() {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public Iterable<V> values() {
        throw new RuntimeException("Not yet implemented.");
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        throw new RuntimeException("Not yet implemented.");
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    protected void checkKey(K k) {
        throw new RuntimeException("Not yet implemented.");
    }


    /**
     * Increase/reduce the size of the hash table and rehashes all the entries.
     */
    protected void rehash(int newCap) {
        throw new RuntimeException("Not yet implemented.");
    }
}
