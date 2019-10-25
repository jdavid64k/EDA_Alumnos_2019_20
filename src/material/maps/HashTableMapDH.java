package material.maps;

/**
 * @param <K> The hey
 * @param <V> The stored value
 */
public class HashTableMapDH<K, V> extends AbstractHashTableMap<K, V> {

    public HashTableMapDH(int size) {
        super(size);
    }

    public HashTableMapDH() {
        super();
    }

    public HashTableMapDH(int p, int cap) {
        super(p, cap);
    }

    @Override
    protected int offset(K key, int i) {
        //TODO: Practica 4 Ejercicio 1
        throw new RuntimeException("Not yet implemented.");
    }
}
