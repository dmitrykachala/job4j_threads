package ru.job4j.storage;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ThreadSafe
public final class UserStorage {

    private final Map<Integer, User> store = new HashMap<>();

    public synchronized boolean add(User user) {
        store.put(user.getId(), user);
        return true;
    }

    public synchronized boolean update(User user) {
        if (store.containsKey(user.getId())) {
            store.replace(user.getId(), user);
            return true;
        }
        return false;
    }

    public synchronized boolean delete(User user) {
        if (store.containsKey(user.getId())) {
            store.remove(user.getId());
            return true;
        }
        return false;
    }

    public synchronized User get(int id) {
        return store.get(id);
    }

    public synchronized boolean transfer(int fromId, int told, int amount) {
        if (!store.containsKey(fromId) || !store.containsKey(told)) {
            return false;
        }
        User userSource = store.get(fromId);
        User userDest = store.get(told);
        userDest.setAmount(userDest.getAmount() + amount);
        userSource.setAmount(userSource.getAmount() - amount);
        return true;
    }

    @Override
    public String toString() {
        return "UserStorage{" + "store=" + store + '}';
    }

    public static void main(String[] args) {
        UserStorage store = new UserStorage();

        store.add(new User(1, 100));
        store.add(new User(2, 200));

        store.transfer(1, 2, 50);

        System.out.println(store);
    }
}
