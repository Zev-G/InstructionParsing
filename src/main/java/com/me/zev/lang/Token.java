package com.me.zev.lang;

public class Token implements Parseable {

    private final String text;
    private final String name;

    private boolean ignoreLeadingWhiteSpace = true;

    public Token(String text) {
        this(text, text);
    }
    public Token(String text, String name) {
        this.text = text;
        this.name = name;
    }

    public static Token[] fromStrings(String... strings) {
        Token[] tokens = new Token[strings.length];
        for (int i = 0, size = strings.length; i < size; i++) {
            tokens[i] = new Token(strings[i]);
        }
        return tokens;
    }

    @Override
    public int matches(String text) {
        int offset = 0;
        if (ignoreLeadingWhiteSpace && !text.isEmpty()) {
            int lenBeforeTrim = text.length();
            while (Text.isWhitespace(text.charAt(0))) {
                text = text.substring(1);
                if (text.isEmpty()) return -1;
            }
            offset = lenBeforeTrim - text.length();
        }
        if (text.startsWith(this.text)) {
            return this.text.length() + offset;
        }
        return -1;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isIgnoreLeadingWhiteSpace() {
        return ignoreLeadingWhiteSpace;
    }

    public void setIgnoreLeadingWhiteSpace(boolean ignoreLeadingWhiteSpace) {
        this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
    }

}
