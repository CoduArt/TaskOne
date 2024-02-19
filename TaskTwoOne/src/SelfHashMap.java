import java.util.*;

public class SelfHashMap<K, V> {
    private ArrayList<K> dictionaryKeys = new ArrayList<>();
    private ArrayList<V> dictionaryValues = new ArrayList<>();

    public void put(K key, V value) {
        addToDictionary(key, value);
    }

    public void putAll(SelfHashMap<K, V> anotherMap) {
        for (int i = 0; i < anotherMap.size(); i++) {
            put(anotherMap.getKey(i), anotherMap.getValue(i));
        }
    }

    public boolean containsKey(K key) {
        for (int i = 0; i < size(); i++) {
            if (getKey(i) == key) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(V value) {
        for (int i = 0; i < size(); i++) {
            if (getValue(i) == value) {
                return true;
            }
        }
        return false;
    }

    public V get(K key) {
        for (int i = 0; i < size(); i++) {
            if (getKey(i) == key) {
                return getValue(i);
            }
        }
        throw new RuntimeException("Ключ не найден");
    }

    public void remove(K key) {
        dictionaryValues.remove(getKeyIndex(key));
        dictionaryKeys.remove(getKeyIndex(key));
    }

    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (K key: dictionaryKeys) {
            set.add(key);
        }
        return set;
    }

    public Collection<V> values() {
        Collection<V> collection = new HashSet<>();
        for (V value: dictionaryValues) {
            collection.add(value);
        }
        return collection;
    }
    
    public SelfHashMap<K, V> clone() {
        SelfHashMap<K, V> clone = new SelfHashMap<>();
        for (int i = 0; i < size(); i++) {
            clone.put(getKey(i), getValue(i));
        }
        return clone;
    }

    public int size() {
        return dictionaryValues.size();
    }

    public void clear() {
        dictionaryKeys.clear();
        dictionaryValues.clear();
    }

    public boolean isEmpty() {
        if (dictionaryKeys.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void print() {
        System.out.print("{");
        for (int i = 0; i < size(); i++) {
            System.out.print(getKey(i) + "=" + getValue(i));
            if (i == size() - 1) {
                break;
            } else {
                System.out.print(", ");
            }
        }
        System.out.println("}");
    }

    private K getKey(int index) {return dictionaryKeys.get(index);}

    private int getKeyIndex(K key) {
        for (int i = 0; i < size(); i++) {
            if (key.equals(getKey(i))) {
                return i;
            }
        }
        throw new RuntimeException("Такого ключа нет");
    }

    private V getValue(int index) {return dictionaryValues.get(index);}

    private void addToDictionary(K key, V value) {
        for (int i = 0; i < size(); i++) {
            if (key.equals(getKey(i))) {
                dictionaryKeys.remove(i);
                dictionaryValues.remove(i);
                dictionaryKeys.add(i, key);
                dictionaryValues.add(i, value);
                return;
            }
        }
        dictionaryKeys.add(key);
        dictionaryValues.add(value);
    }
}
