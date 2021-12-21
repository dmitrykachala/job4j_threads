package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private static final String GET = "GET";
    private static final String POST = "POST";

    private final ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {

        String httpRequestType = req.httpRequestType();
        String poohMode = req.getPoohMode();
        String sourceName = req.getSourceName();
        String param = req.getParam();

        ConcurrentLinkedQueue<String> clq = new ConcurrentLinkedQueue<>();

        if ("queue".equals(poohMode)) {
            if (POST.equals(httpRequestType)) {
                queue.putIfAbsent(sourceName, clq);
            }
        }
        return null;
    }

    public ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> getQueue() {
        return queue;
    }
}
