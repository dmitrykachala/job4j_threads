package ru.job4j.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch extends RecursiveTask<Integer> {

    private final Object[] array;
    private final Object object;
    private final int start;
    private final int finish;

    public ParallelSearch(Object[] array, Object object, int start, int finish) {
        this.array = array;
        this.object = object;
        this.start = start;
        this.finish = finish;
    }

    @Override
    protected Integer compute() {

        int rsl = -1;

        if (finish - start <= 10) {
            for (int i = start; i <= finish; i++) {
                if (array[i].equals(object)) {
                    rsl = i;
                    break;
                }
            }
            return rsl;
        }

        ParallelSearch leftSearch = new ParallelSearch(array, object,
                start, array.length / 2);
        ParallelSearch rightSearch = new ParallelSearch(array, object,
                1 + array.length / 2, finish);

        leftSearch.fork();
        rightSearch.fork();

        return Math.max(leftSearch.join(), rightSearch.join());
    }

    public Integer search() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer rsl = forkJoinPool.invoke(new ParallelSearch(array, object,
                0, array.length - 1));
        System.out.println(rsl);
        return rsl;
    }

    public static void main(String[] args) {
        ParallelSearch ps = new ParallelSearch(new Integer[]{12, 124, 4, 346, 674,
                234, 34, 7, 673, 42, 3, 7, 9, 0, 878, 8, 54, 989, 3244523, 7,
                7}, 7, 0, 20);
        ps.search();
    }
}
