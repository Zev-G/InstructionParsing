package com.me.zev.lang;

public class Comment implements Parseable {

    @Override
    public int matches(String text) {
        if (text.startsWith("//")) {
            //
            int nextLine = text.indexOf("\n");
            int otherNextLine = text.indexOf("\r");
            if (otherNextLine != -1 && otherNextLine < nextLine) {
                nextLine = otherNextLine;
            }
            if (nextLine == -1) return text.length();
            return nextLine;
        } else if (text.startsWith("/*")) {
            /* */
            // TODO allow for a parse error to be sent here if the text doesn't contain "*/"
            return text.indexOf("*/") + 2;
        }
        return -1;
    }

    @Override
    public String getName() {
        return "comment";
    }

}
