package com.me.zev.lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexToken implements Parseable {

    private final Pattern pattern;
    private final String name;

    public RegexToken(String pattern, String name) {
        this(Pattern.compile(pattern), name);
    }
    public RegexToken(Pattern pattern, String name) {
        this.pattern = pattern;
        this.name = name;
    }

    @Override
    public int matches(String text) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.lookingAt()) return matcher.end();
        return -1;
    }

    @Override
    public String getName() {
        return name;
    }

}
