package material.maps;

import java.util.Iterator;
import java.util.Random;


/**
 * <p>This class provides a skeletal implementation of the Map interface. A hash table data
 * structure that uses open addressing to handle collisions. To implement a map the programmer
 * needs to provide an implementation for the {@link #offset(K, int) offset} method which
 * determines de probe sequence.</p>
 *
 * <p>The hash function uses the built-in hashCode method and the
 * multiply-add-and-divide method. The load factor is kept less than or equal to
 * 0.5. When the load factor reaches 0.5, the entries are rehashed into a new
 * bucket array with twice the capacity.</p>
 *
 * @param <K> Key type
 * @param <V> Value type
 * @author R. Cabido, A. Duarte, and J. Velez
 */
abstract public class AbstractHashTableMap<K, V> implements Map<K, V> {

    /**
     * @param <T> Key type
     * @param <U> Value type
     */
    private class HashEntry<T, U> implements Entry<T, U> {

        protected T key;
        protected U value;

        public HashEntry(T k, U v) {
            key = k;
            value = v;
        }

        @Override
        public U getValue() {
            return value;
        }

        @Override
        public T getKey() {
            return key;
        }

        public U setValue(U val) {
            U oldValue = value;
            value = val;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {

            if (o.getClass() != this.getClass()) {
                return false;
            }

            HashEntry<T, U> ent;
            try {
                ent = (HashEntry<T, U>) o;
            } catch (ClassCastException ex) {
                return false;
            }
            return (ent.getKey().equals(this.key))
                    && (ent.getValue().equals(this.value));
        }

        /**
         * Entry visualization.
         */
        @Override
        public String toString() {
            return "(" + key + "," + value + ")";
        }
    }

    /**
     * @author Juan David Quintana Perez, Daniel Arroyo Cortes
     */
    private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {

        private int pos;
        private HashEntry<T, U>[] bucket;
        private Entry<T, U> AVAILABLE;

        public HashTableMapIterator(HashEntry<T, U>[] b, Entry<T, U> av, int numElems) {
            this.bucket = b;
            this.AVAILABLE = av;
            if (numElems == 0) {
                this.pos = bucket.length;
            } else {
                this.pos = 0;
                goToNextElement(0);
            }
        }

        private void goToNextElement(int start) {
            this.pos = start;
            while ((this.pos < bucket.length) && ((this.bucket[this.pos] == null) || (this.bucket[this.pos] == this.AVAILABLE))) {
                this.pos++;
            }
        }

        @Override
        public boolean hasNext() {
            return (this.pos < this.bucket.length);
        }

        @Override
        public Entry<T, U> next() {
            if (hasNext()) {
                int currentPos = this.pos;
                goToNextElement(this.pos + 1);
                return this.bucket[currentPos];
            } else {
                throw new IllegalStateException("The map has not more elements");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {

        public HashTableMapIterator<T, U> it;

        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public T next() {
            return it.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {

        public HashTableMapIterator<T, U> it;

        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public U next() {
            return it.next().getValue();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    protected class HashEntryIndex {

        int index;
        boolean found;

        public HashEntryIndex(int index, boolean f) {
            this.index = index;
            this.found = f;
        }

        //Easy visualization
        @Override
        public String toString() {
            return "(" + this.index + ", " + this.found + ")";
        }
    }

    protected int n; // number of entries in the dictionary
    protected int prime, capacity; // prime factor and capacity of bucket array
    protected long scale, shift; // the shift and scaling factors
    protected HashEntry<K, V>[] bucket;// bucket array
    protected final Entry<K, V> AVAILABLE = new HashEntry<>(null, null);

    /**
     * Creates a hash table with prime factor 109345121 and capacity 1000.
     */
    protected AbstractHashTableMap() {
        this(109345121, 1000); // reusing the constructor HashTableMap(int p, int cap)
    }

    /**
     * Creates a hash table with prime factor 109345121 and given capacity.
     *
     * @param cap initial capacity
     */
    protected AbstractHashTableMap(int cap) {
        this(109345121, cap); // reusing the constructor HashTableMap(int p, int cap)
    }

    /**
     * Creates a hash table with the given prime factor and capacity.
     *
     * @param p   prime number
     * @param cap initial capacity
     */
    protected AbstractHashTableMap(int p, int cap) {
        this.n = 0;
        this.prime = p;
        this.capacity = cap;
        this.bucket = (HashEntry<K, V>[]) new HashEntry[capacity]; // safe cast
        Random rand = new Random();
        this.scale = rand.nextInt(prime - 1) + 1;
        this.shift = rand.nextInt(prime);
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public boolean isEmpty() {
        return (n == 0);
    }

    /**
     * Returns an offset used to calculate the next probe. This value should
     * be added to the initial index for the given key. The returned value
     * should always be 0 for i=0 since that means we haven't had any collisions
     * for this key yet.
     *
     * @param key we are checking/inserting
     * @param i   number of times this key has caused a collision
     * @return the offset to be added to the start index
     */
    abstract protected int offset(K key, int i);

    /**
     * Looks for a given key in the map an returns a HashEntryIndex object
     * with information about where the key was found (if found) or which
     * is the first free location where it could be placed (if not found).
     *
     * @param key the key we are looking for
     * @return A HashEntryIndex
     * @throws IllegalStateException for a null key
     */
    protected HashEntryIndex findEntry(K key) throws IllegalStateException {
        int avail = -1;
        checkKey(key);
        int hashcode = hashValue(key);
        int index = hashcode;
        int retry = 0;
        do {
            Entry<K, V> e = bucket[index];
            if (e == null) {
                if (avail < 0) {
                    avail = index; // key is not in table
                }
                break;
            } else if (key.equals(e.getKey())) { // we have found our key
                return new HashEntryIndex(index, true); // key found
            } else if (e == AVAILABLE) { // bucket is deactivated
                if (avail < 0) {
                    avail = index; // remember that this slot is available
                }
            }
            retry++;
            index = (hashcode + offset(key, retry)) % capacity; // keep looking
        } while (retry < this.capacity);
        return new HashEntryIndex(avail, false); // first empty or available slot
    }

    @Override
    public V get(K key) throws IllegalStateException {
        HashEntryIndex i = findEntry(key); // helper method for finding a key
        if (!i.found) {
            return null; // there is no value for this key, so return null
        }
        return bucket[i.index].getValue(); // return the found value in this case
    }


    @Override
    public V put(K key, V value) throws IllegalStateException {
        HashEntryIndex i = findEntry(key); // find the appropriate spot for this entry
        if (i.found) { // this key has a previous value
            return bucket[i.index].setValue(value); // set new value
        }
        if (n >= capacity / 2) {
            rehash(); // rehash to keep the load factor <= 0.5
            i = findEntry(key); // find again the appropriate spot for this entry
        }
        bucket[i.index] = new HashEntry<>(key, value); // convert to proper entry
        n++;
        return null; // there was no previous value
    }

    @Override
    public V remove(K key) throws IllegalStateException {
        HashEntryIndex i = findEntry(key); // find this key first
        if (!i.found) {
            return null; // nothing to remove
        }
        V toReturn = bucket[i.index].getValue();
        bucket[i.index] = (HashEntry<K, V>) AVAILABLE; // mark this slot as reactivated
        n--;
        return toReturn;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableMapIterator<>(this.bucket, this.AVAILABLE, this.n);

    }

    @Override
    public Iterable<K> keys() {
        return new Iterable<K>() {
            public Iterator<K> iterator() {
                return new HashTableMapKeyIterator<K, V>(new HashTableMapIterator<>(bucket, AVAILABLE, n));
            }
        };
    }

    @Override
    public Iterable<V> values() {
        return new Iterable<V>() {
            public Iterator<V> iterator() {
                return new HashTableMapValueIterator<K, V>(new HashTableMapIterator<>(bucket, AVAILABLE, n));
            }
        };
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        return new Iterable<Entry<K, V>>() {
            public Iterator<Entry<K, V>> iterator() {
                return new HashTableMapIterator<>(bucket, AVAILABLE, n);
            }
        };
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    protected void checkKey(K k) {
        // We cannot check the second test (i.e., k instanceof K) since we do not know the class K
        if (k == null) {
            throw new IllegalStateException("Invalid key: null.");
        }
    }

    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return hash value
     */
    protected int hashValue(K key) {
        return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
    }

    /**
     * Doubles the size of the hash table and rehashes all the entries.
     */
    protected void rehash() {
        capacity = 2 * capacity;
        HashEntry<K, V>[] old = bucket;
        // new bucket is twice as big
        bucket = (HashEntry<K, V>[]) new HashEntry[capacity];
        Random rand = new Random();
        // new hash scaling factor
        scale = rand.nextInt(prime - 1) + 1;
        // new hash shifting factor
        shift = rand.nextInt(prime);
        for (HashEntry<K, V> e : old) {
            if ((e != null) && (e != AVAILABLE)) { // a valid entry
                int j = findEntry(e.getKey()).index;
                bucket[j] = e;
            }
        }
    }

    /**
     * Changes the size of the hash table and rehashes all the entries.
     */
    protected void rehash(int newcap) {
        //Prevent rehashing when decreasing the capacity
        // and the load factor constrain is not met
        if (newcap < 2 * this.size())
            return;

        capacity = newcap;
        HashEntry<K, V>[] old = bucket;
        bucket = (HashEntry<K, V>[]) new HashEntry[capacity];
        Random rand = new Random();
        // new hash scaling factor
        scale = rand.nextInt(prime - 1) + 1;
        // new hash shifting factor
        shift = rand.nextInt(prime);
        for (HashEntry<K, V> e : old) {
            if ((e != null) && (e != AVAILABLE)) { // a valid entry
                int j = findEntry(e.getKey()).index;
                bucket[j] = e;
            }
        }
    }

}
