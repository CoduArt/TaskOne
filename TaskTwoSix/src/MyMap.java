import java.util.*;

public class MyMap<K, V> implements Map<K, V> {
    final int HASH_LENGTH = 3;
    private int size = 0;
    arrayNode hash;

    public MyMap() {
        hash = new arrayNode();
        arrayNode thisHash = hash;
        for (int i = 0; i < HASH_LENGTH - 1; i++) {
            thisHash.next = new arrayNode();
            thisHash = thisHash.next;
        }
    }

    private class arrayNode {
        public SimpleNode element;
        arrayNode next;
    }

    private class SimpleNode {
        public K key;
        public V value;
        public SimpleNode next;

        public SimpleNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private SimpleNode findIdElement(int id) {
        if (id >= HASH_LENGTH) {
            throw new RuntimeException("Выход за пределы hash листа");
        }
        arrayNode thisStack = hash;
        for (int i = 0; i < id; i++) {
            thisStack = thisStack.next;
        }
        return thisStack.element;
    }

    private arrayNode findIdNode(int id) {
        if (id >= HASH_LENGTH) {
            throw new RuntimeException("Выход за пределы hash листа");
        }
        arrayNode thisStack = hash;
        for (int i = 0; i < id; i++) {
            thisStack = thisStack.next;
        }
        return thisStack;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null ? true : false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    private V search(SimpleNode node, String key) {
        if (node == null) {
            return null;
        } else if (node.key.toString().equals(key)) {
            return node.value;
        } else {
            return search(node.next, key);
        }
    }

    @Override
    public V get(Object key) {
        int id = getId(key.toString());
        return search(findIdElement(id), key.toString());
    }

    private int getId(String key) {
        byte[] b = key.getBytes();
        int id = 0;
        for (int i: b) {
            id += i;
        }
        return id % HASH_LENGTH;
    }

    private void add(SimpleNode node,K key, V value) {
        if (node.key.equals(key)) {
            node.value = value;
        } else if (node.next == null) {
            node.next = new SimpleNode(key, value);
            size++;
        } else {
            add(node.next, key, value);
        }
    }

    @Override
    public V put(K key, V value) {
        int id = getId(key.toString());
        if (findIdElement(id) == null) {
            findIdNode(id).element = new SimpleNode(key, value);
            size++;
        } else {
            add(findIdElement(id), key, value);
        }
        return value;
    }

    private V delete(SimpleNode node, String key) {
        if (node.next == null) {
            return null;
        }
        if (node.next.key.toString().equals(key)) {
            V del = node.next.value;
            node.next = node.next.next;
            return del;
        } else {
            return delete(node.next, key);
        }
    }

    @Override
    public V remove(Object key) {
        int id = getId(key.toString());
        V deletedElement = null;
        arrayNode node = findIdNode(id);
        if (node.element == null) {
            return null;
        }else if (node.element.key.equals(key)) {
            deletedElement = node.element.value;
            node.element = node.element.next;
        } else {
            deletedElement = delete(node.element, key.toString());
        }
        return deletedElement;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        arrayNode thisHash = hash;
        for (int i = 0; i < HASH_LENGTH; i++) {
            thisHash.element = null;
            thisHash.next = null;
        }
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
