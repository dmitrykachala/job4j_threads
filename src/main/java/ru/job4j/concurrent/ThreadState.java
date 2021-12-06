package main.java.ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {

        Thread first = new Thread(
                () -> System.out.println("first thread's name: " + Thread.currentThread().getName())
        );

        first.start();

        Thread second = new Thread(
                () -> System.out.println("second thread's name: "
                        + Thread.currentThread().getName())
        );

        second.start();

        System.out.println("first thread's state: " + first.getState());
        System.out.println("second thread's state: " + second.getState());

        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println("first thread's state: " + first.getState());
            System.out.println("second thread's state: " + second.getState());
        }

        System.out.println("Работа завершена!");
    }
}
