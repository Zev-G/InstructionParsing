package com.me.zev.lang;

public class ParsedItem {

    private final Parseable parseable;
    private final String code;

    private final String name;

    public ParsedItem(Parseable parseable, String code) {
        this.parseable = parseable;
        this.code = code;

        this.name = parseable.getName();
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

    public String getFormattedCode() {
        return code.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r");
    }

}
