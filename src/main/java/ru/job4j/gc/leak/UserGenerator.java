package ru.job4j.gc.leak;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserGenerator implements Generate {

    public static final String PATH_NAMES = "src/main/java/ru/job4j/gc/leak/files/names.txt";
    public static final String PATH_SURNAMES = "src/main/java/ru/job4j/gc/leak/files/surnames.txt";
    public static final String PATH_PATRONS = "src/main/java/ru/job4j/gc/leak/files/patr.txt";

    public static final String SEPARATOR = " ";
    public static final int NEW_USERS = 1000;

    private List<String> names;
    private List<String> surnames;
    private List<String> patrons;
    private List<User> users = new ArrayList<>();
    private Random random;

    public UserGenerator(Random random) {
        this.random = random;
        readAll();
    }

    @Override
    public void generate() {
        users.clear();
        for (int i = 0; i < NEW_USERS; i++) {
            StringBuilder sb = new StringBuilder();
            users.add(new User(sb.append(surnames.get(random.nextInt(surnames.size())))
                    .append(SEPARATOR)
                    .append(names.get(random.nextInt(names.size())))
                    .append(SEPARATOR)
                    .append(patrons.get(random.nextInt(patrons.size())))
                    .toString()));
        }
    }

    private void readAll() {
        try {
            names = read(PATH_NAMES);
            surnames = read(PATH_SURNAMES);
            patrons = read(PATH_PATRONS);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public User randomUser() {
        return users.get(random.nextInt(users.size()));
    }

    public List<User> getUsers() {
        return users;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getSurnames() {
        return surnames;
    }

    public void setSurnames(List<String> surnames) {
        this.surnames = surnames;
    }

    public List<String> getPatrons() {
        return patrons;
    }

    public void setPatrons(List<String> patrons) {
        this.patrons = patrons;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}
