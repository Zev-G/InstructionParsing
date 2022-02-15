package com.me.zev.lang;

public final class Parseables {

    public static ParseableExtension endsWord(Parseable parseable) {
        return new ParseableExtension(parseable) {
            @Override
            protected int extendMatch(int prevMatch, String text) {
                String afterMatch = text.substring(prevMatch);
                if (afterMatch.length() == 0 || Character.isWhitespace(afterMatch.charAt(0))) return prevMatch;
                return -1;
            }
        };
    }

    public static ParseableExtension allowLeadingWhiteSpace(Parseable parseable) {
        return new ParseableExtension(parseable) {

            @Override
            public int matches(String text) {
                if (text.isEmpty()) return getParseable().matches(text);
                int ogLen = text.length();
                while (Text.isWhitespace(text.charAt(0))) {
                    text = text.substring(1);
                    if (text.isEmpty()) return -1;
                }
                int offset = ogLen - text.length();
                int match = getParseable().matches(text);
                if (match == -1) return -1;
                return match + offset;
            }

            @Override
            protected int extendMatch(int prevMatch, String text) {
                return -1; // Should never get ran
            }
        };
    }

}
