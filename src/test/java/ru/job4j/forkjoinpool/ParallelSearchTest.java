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

}