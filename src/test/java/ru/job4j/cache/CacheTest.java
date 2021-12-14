package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAdd() {

        Cache cache = new Cache();
        Base b1 = new Base(1, 1);
        cache.add(b1);
        assertThat(cache.get(1), is(b1));

    }

    @Test
    public void whenUpdate() {

        Cache cache = new Cache();
        Base b1 = new Base(1, 1);
        Base b2 = new Base(2, 1);
        cache.add(b1);
        cache.add(b2);
        cache.update(b2);
        assertThat(cache.get(1), is(b1));
        assertThat(cache.get(2).getVersion(), is(2));

    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base b1 = new Base(1, 1);
        Base b2 = new Base(2, 1);
        cache.add(b1);
        cache.add(b2);
        cache.delete(b1);
        assertNull(cache.get(1));
    }

}