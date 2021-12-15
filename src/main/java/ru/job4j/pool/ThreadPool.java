package ru.job4j.pool;

import ru.job4j.sbq.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private int size;
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        this.size = Runtime.getRuntime().availableProcessors();
    }

    public synchronized void run() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                try {
                    while (tasks.getQueueSize() == 0) {
                        Thread.currentThread().wait();
                    }
                    Thread.State ts = Thread.currentThread().getState();
                    if (ts == Thread.State.WAITING) {
                        notify();
                    }
                    tasks.poll();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }));
        }
    }

    public synchronized void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public void shutdown() {
        for (var thread : threads) {
            if (!thread.isInterrupted()) {
                thread.interrupt();
            }
        }
    }
}
