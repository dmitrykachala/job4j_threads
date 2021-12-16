package ru.job4j.pool;

import ru.job4j.sbq.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                System.out.println("Thread started.");
                synchronized (this) {
                    try {
                        while (!Thread.currentThread().isInterrupted()) {
                            if (Thread.currentThread().getState() == Thread.State.WAITING) {
                                notify();
                            }
                            if (tasks.getQueueSize() == 0) {
                                wait();
                            } else {
                                tasks.poll().run();
                            }
                        }
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }));
        }
        for (var thread : threads) {
            thread.start();
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
        notifyAll();
    }

    public void shutdown() {
        for (var thread : threads) {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
    }
}


