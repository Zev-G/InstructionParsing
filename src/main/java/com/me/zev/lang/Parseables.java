package com.me.zev.lang;

public final class Parseables {

    public static Parseable endsWord(Parseable parseable) {
        return new ParseableExtension(parseable) {
            @Override
            protected int extendMatch(int prevMatch, String text) {
                String afterMatch = text.substring(prevMatch);
                if (afterMatch.length() == 0 || Character.isWhitespace(afterMatch.charAt(0))) return prevMatch;
                return -1;
            }
        };
    }

}
