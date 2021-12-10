package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
            try {
                BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                byte[] dataBuffer = new byte[1024];
                int bytesRead;

                long bytesWrited = 0;

                long startTime = System.currentTimeMillis();
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    bytesWrited = bytesWrited + bytesRead;
                    if (bytesWrited >= speed) {
                        long endTime = System.currentTimeMillis();
                        long time = endTime - startTime;
                        if (time < 1000) {
                            Thread.sleep(1000 - time);
                            System.out.println("Pause...");
                        }
                        bytesWrited = 0;
                        startTime = System.currentTimeMillis();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    }

    public static boolean validateArgs(int argsNum) throws IllegalArgumentException {
        if (argsNum != 3) {
            throw new IllegalArgumentException();
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        if (Wget.validateArgs(args.length)) {
            String url = args[0];
            int speed = Integer.parseInt(args[1]);
            String fileName = args[2];
            Thread wget = new Thread(new Wget(url, speed, fileName));
            wget.start();
            wget.join();
        }
    }
}
