package ru.job4j.pool;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class ThreadPoolTest {

    @Test
    public void whenTPWork() {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        ThreadPool tp = new ThreadPool();
        try {
            tp.work(() -> buffer.add(0));
            tp.work(() -> buffer.add(1));
            tp.work(() -> buffer.add(2));
            tp.work(() -> buffer.add(3));
            tp.work(() -> buffer.add(4));
            tp.work(() -> buffer.add(5));
            tp.work(() -> buffer.add(6));
            tp.work(() -> buffer.add(7));
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        tp.shutdown();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        assertThat(buffer.size(), is(8));
    }
}