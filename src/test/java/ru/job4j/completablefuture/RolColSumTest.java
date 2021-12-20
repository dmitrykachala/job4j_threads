package ru.job4j.completablefuture;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void whenOrdinarySum() {
        int[][] matrix = {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}};
        RolColSum.Sums[] rsl = RolColSum.sum(matrix);
        RolColSum.Sums[] expected = {new RolColSum.Sums(3, 6),
                new RolColSum.Sums(6, 6), new RolColSum.Sums(9, 6)};
        assertThat(rsl, is(expected));
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 1, 1, 1}, {2, 2, 2, 2}, {3, 3, 3, 3}, {4, 4, 4, 4}};
        RolColSum.Sums[] rsl = RolColSum.asyncSum(matrix);
        RolColSum.Sums[] expected = {new RolColSum.Sums(4, 10),
                new RolColSum.Sums(8, 10), new RolColSum.Sums(12, 10),
                new RolColSum.Sums(16, 10)};
        assertThat(rsl, is(expected));
    }

}