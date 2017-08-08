import java.util.List;
import java.util.Set;
import java.util.NoSuchElementException;

/**
 * Your implementation of HashMap.
 *
 * @author Rares Cristian
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        table = (MapEntry<K, V>[])
            new MapEntry[HashMapInterface.INITIAL_CAPACITY];
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
    }

    /**
     * Returns the absolute value of input integer.
     *
     * @param v an integer
     * @return absolute value of v
     */
    private int abs(int v) {
        if (v < 0) {
            return -v;
        }
        return v;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Cannot search for null key");
        }

        double loadFactor = (size + 1) * 1.0 / table.length * 1.0;
        if (loadFactor > HashMapInterface.MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }

        int hash = abs(key.hashCode()) % table.length;
        int firstRemovedIndex = -1;
        for (int i = 0; i < table.length; i++) {
            int index = (hash + i * i) % table.length;
            if (table[index] == null) {
                size++;
                if (firstRemovedIndex != -1) {
                    table[firstRemovedIndex].setValue(value);
                    table[firstRemovedIndex].setKey(key);
                    table[firstRemovedIndex].setRemoved(false);
                } else {
                    table[index] = new MapEntry<K, V>(key, value);
                }
                return null;
            }

            if (table[index].isRemoved() && firstRemovedIndex == -1) {
                firstRemovedIndex = index;
            }

            if (!table[index].isRemoved()
                && table[index].getKey().equals(key)) {
                V last = table[index].getValue();
                table[index].setValue(value);
                return last;
            }
        }

        if (firstRemovedIndex != -1) {
            table[firstRemovedIndex].setValue(value);
            table[firstRemovedIndex].setRemoved(false);
            size++;
            return null;
        }
        resizeBackingTable(table.length * 2 + 1);
        return put(key, value);
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot remove a null element");
        }

        int hash = abs(key.hashCode()) % table.length;
        for (int i = 0; i < table.length; i++) {
            int index = (hash + i * i) % table.length;
            if (table[index] == null) {
                throw new NoSuchElementException("Key was not found in map");
            }
            if (!table[index].isRemoved()
                && table[index].getKey().equals(key)) {
                V last = table[index].getValue();
                table[index].setRemoved(true);
                size--;
                return last;
            }
        }

        throw new NoSuchElementException("This key was not found in map");
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot search for null key");
        }

        int hash = abs(key.hashCode()) % table.length;
        for (int i = 0; i < table.length; i++) {
            int index = (hash + i * i) % table.length;
            if (table[index] == null) {
                throw new NoSuchElementException("Key was not found in map");
            }
            if (!table[index].isRemoved()
                && table[index].getKey().equals(key)) {
                return table[index].getValue();
            }
        }

        throw new NoSuchElementException("This key was not found in map");
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot search for null key");
        }

        int hash = abs(key.hashCode()) % table.length;
        for (int i = 0; i < table.length; i++) {
            int index = (hash + i * i) % table.length;
            if (table[index] == null) {
                return false;
            }
            if (!table[index].isRemoved()
                && table[index].getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void clear() {
        size = 0;
        table = (MapEntry<K, V>[])
            new MapEntry[HashMapInterface.INITIAL_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new java.util.HashSet<K>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                set.add(table[i].getKey());
            }
        }
        return set;
    }

    @Override
    public List<V> values() {
        List<V> list = new java.util.LinkedList<V>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                list.add(table[i].getValue());
            }
        }
        return list;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Cannot resize table to "
                + "nonpositive value.");
        }

        MapEntry<K, V>[] temp = (MapEntry<K, V>[]) new MapEntry[length];
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                int hash = abs(table[i].getKey().hashCode()) % temp.length;
                boolean placedKey = false;
                for (int k = 0; k < temp.length && !placedKey; k++) {
                    int index = (hash + k * k) % temp.length;
                    if (temp[index] == null) {
                        temp[index] = new MapEntry<K, V>(table[i].getKey(),
                                                        table[i].getValue());
                        placedKey = true;
                    }
                }
            }
        }
        table = temp;
    }

    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

}