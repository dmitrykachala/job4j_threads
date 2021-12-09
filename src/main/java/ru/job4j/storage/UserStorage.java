package ru.job4j.storage;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public final class UserStorage {

    private final List<User> store = new ArrayList<>();

    public User findById(int id) {
        for (var user : store) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public synchronized boolean add(User user) {
        return store.add(user);
    }

    public synchronized boolean update(User user) {
        if (store.contains(user)) {
            store.set(store.indexOf(user), user);
            return true;
        }
        return false;
    }

    public synchronized boolean delete(User user) {
        return store.remove(user);
    }

    public synchronized boolean transfer(int fromId, int told, int amount) {
        if (findById(fromId) == null || findById(told) == null) {
            return false;
        }
        User userSource = findById(fromId);
        User userDest = findById(told);
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
