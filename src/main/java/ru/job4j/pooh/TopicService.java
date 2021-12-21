package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String,
            ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {

        String httpRequestType = req.httpRequestType();
        String poohMode = req.getPoohMode();
        String sourceName = req.getSourceName();
        String param = req.getParam();

        ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> chm;
        ConcurrentLinkedQueue<String> clq;

        if ("topic".equals(poohMode)) {
            if ("POST".equals(httpRequestType)) {
                for (var key : topics.keySet()) {
                    if (topics.get(key).get(sourceName) != null) {
                        chm = topics.get(key);
                        clq = chm.get(sourceName);
                        clq.add(param);
                        chm.putIfAbsent(sourceName, clq);
                        topics.putIfAbsent(key, chm);
                    }
                }
            } else {
                if (topics.get(param) != null) {
                    if (topics.get(param).get(sourceName) != null) {
                        clq = topics.get(param).get(sourceName);
                        return new Resp(clq.poll(), "200");
                    }
                    clq = new ConcurrentLinkedQueue<>();
                    clq.add("");
                    return new Resp(clq.poll(), "204");
                }
                chm = new ConcurrentHashMap<>();
                clq = new ConcurrentLinkedQueue<>();
                clq.add("");
                chm.putIfAbsent(sourceName, clq);
                topics.putIfAbsent(param, chm);
                return new Resp(clq.poll(), "204");
            }
        }
        return null;
    }
}

