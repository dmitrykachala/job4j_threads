package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp;
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).add(req.getParam());
            resp = new Resp("", "200 OK");
        } else if ("GET".equals(req.httpRequestType())) {
            var text = queue.get(req.getSourceName()).poll();
            resp = new Resp(text == null ? "" : text, text == null ? "204 EMPTY" : "200 OK");
        } else {
            resp = new Resp("", "501 Not Implemented");
        }
        return resp;
    }

    public ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> getQueue() {
        return queue;
    }
}
