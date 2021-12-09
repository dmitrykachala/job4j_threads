package ru.job4j.storage;

import junit.framework.TestCase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class UserStorageTest extends TestCase {

    public void testAdd() {
        UserStorage store = new UserStorage();
        User usr1 = new User(1, 100);
        store.add(usr1);
        User rsl = store.get(1);
        assertThat(usr1, is(rsl));
    }

    public void testUpdate() {
        UserStorage store = new UserStorage();
        User usr1 = new User(1, 100);
        User usr2 = new User(1, 200);
        store.add(usr1);
        store.update(usr2);
        User rsl = store.get(1);
        assertThat(usr2, is(rsl));
    }

    public void testDelete() {
        UserStorage store = new UserStorage();
        User usr1 = new User(1, 100);
        User usr2 = new User(2, 200);
        store.add(usr1);
        store.add(usr2);
        store.delete(usr1);
        assertNull(store.get(1));
    }

    public void testTransfer() {
        UserStorage store = new UserStorage();
        User usr1 = new User(1, 100);
        User usr2 = new User(2, 200);
        store.add(usr1);
        store.add(usr2);
        store.transfer(2, 1, 50);
        assertThat(usr1.getAmount(), is(150));
        assertThat(usr2.getAmount(), is(150));
    }
}