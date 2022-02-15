package com.me.zev.lang;

import java.security.InvalidParameterException;

public abstract class ParseableExtension implements Parseable {

    private final Parseable parseable;

    public ParseableExtension(Parseable parseable) {
        if (parseable == null) throw new InvalidParameterException();
        this.parseable = parseable;
    }

    public Parseable getParseable() {
        return parseable;
    }

    @Override
    public int matches(String text) {
        int match = parseable.matches(text);
        if (match == -1) return -1;
        return extendMatch(match, text);
    }

    protected abstract int extendMatch(int prevMatch, String text);

    @Override
    public String getName() {
        return parseable.getName();
    }

    public ParseableExtension endsWord() {
        return Parseables.endsWord(this);
    }

    public ParseableExtension allowLeadingWhiteSpace() {
        return Parseables.allowLeadingWhiteSpace(this);
    }

}
