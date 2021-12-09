package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent() {
        return getInfo(data -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return getInfo(data -> data < 0x80);
    }

    public synchronized String getInfo(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return output.toString();
    }

}
