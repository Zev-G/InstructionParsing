package com.me.zev.lang;

public class ParsedItem {

    private final Parseable parseable;
    private final String code;

    public ParsedItem(Parseable parseable, String code) {
        this.parseable = parseable;
        this.code = code;
    }

}
