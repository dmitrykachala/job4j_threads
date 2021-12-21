package ru.job4j.pooh;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String ls = System.lineSeparator();
        String[] lines = content.split(ls);
        String[] parts = lines[0].split(" ");
        String requestType = parts[0];
        parts = parts[1].split("/");
        String poohMode = parts[1];
        String sourceName = parts[2];
        String param = lines[lines.length - 2].isEmpty() ? lines[lines.length - 1]
                : (parts.length > 3 ? parts[3] : "");
        return new Req(requestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
