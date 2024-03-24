package TaskPacage;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public interface NullQueue<T> extends Queue<T> {

    @Override
    public boolean isEmpty();

    @Override
    public boolean add(T t);

    @Override
    public T remove();

    @Override
    public T element();

    @Override
    public default boolean remove(Object o) {
        return false;
    }

    @Override
    public default int size() {
        return 0;
    }

    @Override
    public default boolean contains(Object o) {
        return false;
    }

    @Override
    public default Iterator<T> iterator() {
        return null;
    }

    @Override
    public default Object[] toArray() {
        return new Object[0];
    }

    @Override
    public default <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public default boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public default boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public default boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public default boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public default void clear() {

    }

    @Override
    public default boolean offer(T t) {
        return false;
    }

    @Override
    public default T poll() {
        return null;
    }


    @Override
    public default T peek() {
        return null;
    }
}
