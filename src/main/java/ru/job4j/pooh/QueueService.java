package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {

        String httpRequestType = req.httpRequestType();
        String poohMode = req.getPoohMode();
        String sourceName = req.getSourceName();
        String param = req.getParam();

        ConcurrentLinkedQueue<String> clq;

        if ("queue".equals(poohMode)) {
            if ("POST".equals(httpRequestType)) {
                if (queue.get(sourceName) != null) {
                    clq = queue.get(sourceName);
                    clq.add(param);
                    queue.putIfAbsent(sourceName, clq);
                } else {
                    clq = new ConcurrentLinkedQueue<>();
                }
                clq.add(param);
                queue.putIfAbsent(sourceName, clq);
            } else {
                clq = queue.get(sourceName);
                return new Resp(clq.poll(), "200");
            }
        }
        return null;
    }

    public ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> getQueue() {
        return queue;
    }
}
