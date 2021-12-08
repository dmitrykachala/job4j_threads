package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() {
        return getInfo(data -> true);
    }

    public String getContentWithoutUnicode() {
        return getInfo(data -> data < 0x80);
    }

    public String getInfo(Predicate<Character> filter) {
        String output = "";
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return output;
    }

}
