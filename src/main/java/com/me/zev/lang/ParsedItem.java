package com.me.zev.lang;

public class ParsedItem {

    private final Parseable parseable;
    private final ParserSettings settings;
    private final int start;
    private final int end;

    private final String name;

    public ParsedItem(Parseable parseable, ParserSettings settings, int start, int end) {
        this.parseable = parseable;

        this.name = parseable.getName();
        this.settings = settings;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "ParsedItem{" +
                "code='" + getFormattedCode() + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return settings.getCode().substring(start, end);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getFormattedCode() {
        return getCode().replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r");
    }

}
