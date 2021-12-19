package ru.job4j.forkjoinpool;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ParallelSearchTest {

    @Test
    public void whenTestPS() {
        ParallelSearch ps = new ParallelSearch(new Integer[]{12, 124, 4, 346, 674,
                234, 34, 7, 673, 42, 3}, 7, 0, 10);
        int rsl = ps.search();
        assertThat(rsl, is(7));
    }

    @Test
    public void whenTestPSNotFound() {
        ParallelSearch ps = new ParallelSearch(new Integer[]{12, 124, 4, 346, 674,
                234, 34, 7, 673, 42, 3, 7, 9, 0, 878, 8, 54, 989, 3244523, 7,
                7}, -5, 0, 20);
        int rsl = ps.search();
        assertThat(rsl, is(-1));
    }

    @Test
    public void whenTestPSAndFoundLast() {
        ParallelSearch ps = new ParallelSearch(new Integer[]{12, 124, 4, 346, 674,
                234, 34, 7, 673, 42, 3, 7, 9, 0, 878, 8, 54, 989, 3244523, 7,
                7}, 7, 0, 20);
        int rsl = ps.search();
        assertThat(rsl, is(20));
    }

}