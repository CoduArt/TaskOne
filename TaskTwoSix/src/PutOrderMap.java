import java.util.*;

public class PutOrderMap<K, V> implements Map<K, V>, Iterable<Block<K, V>> {
    private Map<K, Block<Integer, V>> orderVal;
    private Map<Integer, Block<K, V>> keyVal;
    private int elementNumber = 0;

    public PutOrderMap(MyMap<K, V> a) {
        orderVal = new MyMap<>();
        keyVal = new MyMap<>();
    }

    public PutOrderMap(HashMap<K, V> a) {
        orderVal = new HashMap<>();
        keyVal = new HashMap<>();
    }

    @Override
    public Iterator<Block<K, V>> iterator() {
        return new Iterator<>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                while (!keyVal.containsKey(count) && count <= elementNumber) {
                    count++;
                }
                return count <= elementNumber;
            }

            @Override
            public Block next() throws IndexOutOfBoundsException {
                Block<K, V> block = keyVal.get(count);
                keyVal.remove(count++);
                return block;
            }
        };
    }

    @Override
    public int size() {
        return orderVal.size();
    }

    @Override
    public boolean isEmpty() {
        return orderVal.isEmpty();
    }

    @Override
    public V get(Object key) {
        if (orderVal.get(key) == null) {
            return null;
        }
        return orderVal.get(key).second;
    }

    @Override
    public V put(K key, V value) {
        if (orderVal.containsKey(key)) {
            Block<Integer, V> block = orderVal.get(key);
            orderVal.put(key, new Block<>(block.first, value));
            keyVal.put(block.first, new Block<>(key, value));
        } else {
            orderVal.put(key, new Block<>(elementNumber, value));
            keyVal.put(elementNumber, new Block<>(key, value));
            elementNumber++;
        }
        return value;
    }

    private void countMinus(Integer number) {
        while (number <= elementNumber) {

        }
    }

    @Override
    public V remove(Object key) {
        Block<Integer, V> block = orderVal.remove(key);
        keyVal.remove(block.first);
        elementNumber--;
        return block.second;
    }

    @Override
    public void clear() {
        orderVal.clear();
        keyVal.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
