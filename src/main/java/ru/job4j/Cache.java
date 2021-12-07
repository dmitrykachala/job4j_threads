package ru.job4j;

public class Cache {
    private static Cache cache;
    private static final Object LOCK = new Object();

    public static Cache instOf() {
        if (cache == null) {
            synchronized (LOCK) {
                if (cache == null) {
                    cache = new Cache();
                }
            }
        }
        return cache;
    }
}
