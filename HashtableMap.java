import java.util.NoSuchElementException;

class Pair<KeyType, ValueType> {
    KeyType key;
    ValueType value;

    Pair(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }
}

public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
    private Pair<KeyType, ValueType>[] table;
    private int size;
    private final Pair<KeyType, ValueType> DELETED;

    @SuppressWarnings("unchecked")
    public HashtableMap(int capacity) {
        table = (Pair<KeyType, ValueType>[]) new Pair[capacity];
        size = 0;
        DELETED = new Pair<>(null, null);
    }

    public HashtableMap() {
        this(8);
    }

    private void resize() {
        int newCapacity = 2 * table.length;
        Pair<KeyType, ValueType>[] oldTable = table;
        table = (Pair<KeyType, ValueType>[]) new Pair[newCapacity];
        size = 0;

        for (Pair<KeyType, ValueType> pair : oldTable) {
            if (pair != null && pair != DELETED) {
                put(pair.key, pair.value);
            }
        }
    }

    @Override
    public boolean containsKey(KeyType key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = Math.abs(key.hashCode()) % getCapacity();
        while (table[index] != null) {
            if (table[index] != DELETED && table[index].key.equals(key)) {
                return true;
            }
            index = (index + 1) % getCapacity();
        }

        return false;
    }

    @Override
    public void put(KeyType key, ValueType value) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = Math.abs(key.hashCode()) % getCapacity();
        while (table[index] != null && !table[index].key.equals(key)) {
            index = (index + 1) % getCapacity();
        }

        if (table[index] != null && table[index] != DELETED && table[index].key.equals(key)) {
            throw new IllegalArgumentException("Duplicate key");
        }

        if ((double)(size + 1) / getCapacity() >= 0.7) {
            resize();
            index = Math.abs(key.hashCode()) % getCapacity();
            while (table[index] != null && !table[index].key.equals(key)) {
                index = (index + 1) % getCapacity();
            }
        }

        if (table[index] == null || table[index] == DELETED) {
            size++;  
        }

        table[index] = new Pair<>(key, value);
    }


    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = Math.abs(key.hashCode()) % getCapacity();
        while (table[index] != null) {
            if (table[index] != DELETED && table[index].key.equals(key)) {
                return table[index].value;
            }
            index = (index + 1) % getCapacity();
        }

        throw new NoSuchElementException("Key not found in hashtable");
    }

    @Override
    public ValueType remove(KeyType key) throws NoSuchElementException {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = Math.abs(key.hashCode()) % getCapacity();

        while (table[index] != null && !table[index].key.equals(key)) {
            index = (index + 1) % getCapacity();
        }

        if (table[index] == null || !table[index].key.equals(key)) {
            throw new NoSuchElementException("Key not found in hashtable");
        }

        ValueType value = table[index].value;
        table[index] = DELETED;
        size--;

        return value;
    }

    @Override
    public void clear() {
        for (int i = 0; i < getCapacity(); i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getCapacity() {
        return table.length;
    }
}
