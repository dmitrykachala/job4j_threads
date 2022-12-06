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
    public static final Integer NEW_USERS = 1000;

    private static List<String> names;
    private static List<String> surnames;
    private static List<String> patrons;
    private static List<User> users = new ArrayList<>();
    private Random random;

    public UserGenerator(Random random) {
        this.random = random;
        readAll();
    }

    @Override
    public void generate() {
        users.clear();
        for (int i = 0; i < NEW_USERS; i++) {
            users.add(new User(
                    surnames.get(random.nextInt(surnames.size())) + SEPARATOR
                            + names.get(random.nextInt(names.size())) + SEPARATOR
                            + patrons.get(random.nextInt(patrons.size()))));
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

    public static List<User> getUsers() {
        return users;
    }

    public static List<String> getNames() {
        return names;
    }

    public static void setNames(List<String> names) {
        UserGenerator.names = names;
    }

    public static List<String> getSurnames() {
        return surnames;
    }

    public static void setSurnames(List<String> surnames) {
        UserGenerator.surnames = surnames;
    }

    public static List<String> getPatrons() {
        return patrons;
    }

    public static void setPatrons(List<String> patrons) {
        UserGenerator.patrons = patrons;
    }
}
