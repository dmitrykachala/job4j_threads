package ru.job4j.forkjoinpool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch extends RecursiveTask<Integer> {

    private final Object[] array;
    private final Object object;

    public ParallelSearch(Object[] array, Object object) {
        this.array = array;
        this.object = object;
    }

    @Override
    protected Integer compute() {
        if (array.length <= 10) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(object)) {
                    return i;
                }
            }
        }
        int mid = array.length / 2;
        Object[] leftArray = Arrays.copyOfRange(array, 0, mid);
        Object[] rightArray = Arrays.copyOfRange(array, mid + 1, array.length - 1);

        ParallelSearch leftSearch = new ParallelSearch(leftArray, object);
        ParallelSearch rightSearch = new ParallelSearch(rightArray, object);

        leftSearch.fork();
        rightSearch.fork();

        return leftSearch.join() + rightSearch.join();
    }

    public void search() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println(forkJoinPool.invoke(new ParallelSearch(array, object)));
    }

    public static void main(String[] args) {
        ParallelSearch ps = new ParallelSearch(new Integer[]{12, 124, 4, 346, 674,
                234, 34, 7, 673, 42, 3}, 7);
        ps.search();
    }
}
