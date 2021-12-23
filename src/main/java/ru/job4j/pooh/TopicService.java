package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp;
        if ("POST".equals(req.httpRequestType())) {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            topics.get(req.getSourceName()).values().forEach(q -> q.add(req.getParam()));
            resp = new Resp("", "200 OK");
        } else if ("GET".equals(req.httpRequestType())) {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            topics.get(req.getSourceName()).putIfAbsent(req.getParam(),
                    new ConcurrentLinkedQueue<>());
            var text = topics.get(req.getSourceName()).get(req.getParam()).poll();
            resp = new Resp(text == null ? "" : text, text == null ? "204 EMPTY" : "200 OK");
        } else {
            resp = new Resp("", "501 Not Implemented");
        }
        return resp;
    }
}

