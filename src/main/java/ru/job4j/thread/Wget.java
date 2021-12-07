package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private static final int LIMIT = 1024;
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                FileOutputStream fileOutputStream = new FileOutputStream("7z.exe");
                byte[] dataBuffer = new byte[LIMIT];
                int bytesRead;
                long startTime = System.currentTimeMillis();

                while ((bytesRead = in.read(dataBuffer, 0, LIMIT)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    long time = System.currentTimeMillis();
                    if (time - startTime  < speed) {
                        Thread.sleep(speed - (time - startTime));
                        System.out.println("Pause...");
                    }
                    startTime = System.currentTimeMillis();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
        }
    }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
