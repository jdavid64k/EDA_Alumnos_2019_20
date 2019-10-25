package material.ordereddictionary;

import material.Position;
import material.tree.binarysearchtree.BinarySearchTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Realization of a dictionary by means of a binary search tree.
 *
 * @author A. Duarte, J. Vélez
 */
abstract public class AbstractTreeOrderedDict<K, V> implements
        OrderedDictionary<K, V> {

    /**
     * Nested class for location-aware binary search tree entries
     */
    private class DictEntry<T, U> implements Entry<T, U>,
            Comparable<Entry<T, U>> {

        protected T key;
        protected U value;
        protected Position<Entry<T, U>> pos;
        protected Comparator<T> keyComparator;

        DictEntry(T k, U v, Position<Entry<T, U>> p) {
            this.key = k;
            this.value = v;
            this.pos = p;
            this.keyComparator = null;
        }

        DictEntry(T k, U v, Position<Entry<T, U>> p, Comparator<T> keyComparator) {
            this(k, v, p);
            this.keyComparator = keyComparator;
        }

        public T getKey() {
            return key;
        }

        public U getValue() {
            return value;
        }

        public void setPosition(Position<Entry<T, U>> pos) {
            this.pos = pos;
        }

        public Position<Entry<T, U>> position() {
            return pos;
        }

        protected int compareHash(T k) {
            if (this.hashCode() == k.hashCode()) {
                return 0;
            } else if (this.hashCode() > k.hashCode()) {
                return 1;
            } else {
                return -1;
            }
        }

        @Override
        public int compareTo(Entry<T, U> o) {
            if (this.keyComparator != null) {
                return this.keyComparator.compare(key, o.getKey());
            } else if (this.key instanceof Comparable<?>) {
                return ((Comparable<T>) this.key).compareTo(o.getKey());
            } else {
                return compareHash(o.getKey());
            }
        }

    }

    protected BinarySearchTree<Entry<K, V>> bsTree;
    protected Comparator<K> keyComparator;

    /**
     * Creates a BinarySearchTree with a default comparator.
     */
    public AbstractTreeOrderedDict() {
        bsTree = this.createTree();
        this.keyComparator = null;
    }

    public AbstractTreeOrderedDict(Comparator<K> keyComparator) {
        bsTree = this.createTree();
        this.keyComparator = keyComparator;
    }

    abstract protected BinarySearchTree<Entry<K, V>> createTree();

    /**
     * Checks whether a given key is valid.
     */
    protected void checkKey(K key) {
        if (key == null) // just a simple test for now
        {
            throw new RuntimeException("null key");
        }
    }

    /**
     * Checks whether a given entry is valid.
     */
    protected DictEntry<K, V> checkEntry(Entry<K, V> ent) {
        if (ent == null || !(ent instanceof DictEntry)) {
            throw new RuntimeException("invalid entry");
        }
        return (DictEntry<K, V>) ent;
    }

    /**
     * Returns the number of entries in the tree.
     */
    @Override
    public int size() {
        return this.bsTree.size();
    }

    /**
     * Returns whether the tree is empty.
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns an entry containing the given key. Returns null if no such entry
     * exists.
     */
    @Override
    public Entry<K, V> find(K key) {
        checkKey(key); // may throw an InvalidKeyException
        Entry<K, V> entry = new DictEntry<>(key, null, null);
        Position<Entry<K, V>> pos = this.bsTree.find(entry);
        if (pos != null) {
            DictEntry<K, V> aux = (DictEntry<K, V>) pos.getElement();
            aux.setPosition(pos);
            return aux;
        } else {
            return null;
        }
    }

    /**
     * Returns an iterable collection of all the entries containing the given
     * key.
     */
    @Override
    public Iterable<Entry<K, V>> findAll(K key) {
        checkKey(key); // may throw an InvalidKeyException
        Entry<K, V> entry = new DictEntry<>(key, null, null);

        Iterable<? extends Position<Entry<K, V>>> posList = this.bsTree
                .findAll(entry);
        List<Entry<K, V>> entryList = new ArrayList<>();

        for (Position<Entry<K, V>> pos : posList) {
            DictEntry<K, V> aux = (DictEntry<K, V>) pos.getElement();
            aux.setPosition(pos);
            entryList.add(aux);
        }

        return entryList;
    }

    /**
     * Inserts an entry into the tree and returns the newly created entry.
     */
    @Override
    public Entry<K, V> insert(K k, V v) {
        checkKey(k); // may throw an InvalidKeyException
        DictEntry<K, V> entry = new DictEntry<>(k, v, null, keyComparator);
        Position<Entry<K, V>> pos = this.bsTree.insert(entry);
        entry.setPosition(pos);
        return entry;
    }

    /**
     * Removes and returns a given entry.
     */
    @Override
    public void remove(Entry<K, V> ent) {        
        DictEntry<K, V> entry = checkEntry(ent);
        Position<Entry<K, V>> p = entry.position();
        this.bsTree.remove(p);
    }

    public Iterable<Entry<K, V>> findRange(K minkey, K maxkey) {
        throw new RuntimeException("Not implemented.");
    }

}
