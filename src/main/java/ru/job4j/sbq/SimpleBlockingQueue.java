package ru.job4j.sbq;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
            wait();
        }
        queue.offer(value);
        notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
        T el = queue.poll();
        notifyAll();
        return el;
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(() -> {
            try {
                sbq.offer(1);
                sbq.offer(2);
                sbq.offer(3);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                sbq.poll();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        });
        producer.start();
        producer.join();
        System.out.println(sbq.queue.size());
        consumer.start();
        consumer.join();
        System.out.println(sbq.queue.size());
    }
}
