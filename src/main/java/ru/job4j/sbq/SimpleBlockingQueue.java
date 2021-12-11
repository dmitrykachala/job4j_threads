package ru.job4j.sbq;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) {
        System.out.println("Offering value: " + value + "...");
        try {
            while (queue.size() == limit) {
                System.out.println("Queue is full. Waiting...");
                wait();
            }
            if (queue.size() == 0) {
                notifyAll();
            }
            queue.offer(value);
            System.out.println(value + " was put.");
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public synchronized T poll() {
        System.out.println("Polling...");
        try {
            while (queue.size() == 0) {
                System.out.println("Queue is empty. Waiting...");
                wait();
            }
            if (queue.size() == limit) {
                System.out.println("Queue is full! Wake up, make a move!");
                notifyAll();
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        System.out.println(queue.poll() + " was polled.");
        return queue.poll();
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(() -> {
            System.out.println("Producer");
            sbq.offer(1);
            sbq.offer(2);
            sbq.offer(3);
        });
        Thread consumer = new Thread(() -> {
            System.out.println("Consumer");
            sbq.poll();
        });
        producer.start();
        producer.join();
        System.out.println(sbq.queue.size());
        consumer.start();
        consumer.join();
        System.out.println(sbq.queue.size());
    }
}
