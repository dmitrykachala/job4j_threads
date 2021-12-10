package ru.job4j.synch;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public final class SingleLockList<T> implements Iterable<T> {
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = new ArrayList<>(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public T get(int index) {
        return list.get(index);
    }

    public Iterator<T> copy(Iterator<T> iterator) {
        Collection<T> copy = new ArrayList<>();
        while (iterator.hasNext()) {
            copy.add(iterator.next());
        }
        return copy.iterator();
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.list.iterator());
    }
}
