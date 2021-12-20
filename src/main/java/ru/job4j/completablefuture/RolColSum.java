package ru.job4j.completablefuture;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {

        private int rowSum;
        private int colSum;

        public Sums() {

        }

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }

        @Override
        public String toString() {
            return "Sums{" + "rowSum=" + rowSum + ", colSum=" + colSum + '}';
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        int rowSum;
        int colSum;
        for (int i = 0; i < matrix.length; i++) {
            rowSum = 0;
            colSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i] = new Sums(rowSum, colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws InterruptedException, ExecutionException {
        Sums[] sums = new Sums[matrix.length];
        CompletableFuture<int[]> rows = getRowSum(matrix);
        CompletableFuture<int[]> columns = getColSum(matrix);
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums(rows.get()[i], columns.get()[i]);
        }
        return sums;
    }

    public static CompletableFuture<int[]> getRowSum(int[][] matrix) {
        return CompletableFuture.supplyAsync(() -> {

            int[] rowSum = new int[matrix.length];

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    rowSum[i] += matrix[i][j];
                }
            }
            return rowSum;
        });
    }

    public static CompletableFuture<int[]> getColSum(int[][] matrix) {
        return CompletableFuture.supplyAsync(() -> {

            int[] colSum = new int[matrix.length];

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    colSum[i] += matrix[j][i];
                }
            }
            return colSum;
        });
    }

}
