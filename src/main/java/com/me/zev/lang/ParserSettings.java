package com.me.zev.lang;

import java.util.ArrayList;
import java.util.List;

public final class ParserSettings {

    private final List<Parseable> parseables = new ArrayList<>();

    private final ErrorHandler<ParsingError> errorHandler;
    private final String path;
    private final String code;

    private final String[] lines;
    private final int lineCount;

    public ParserSettings(String path, String code) {
        this(ErrorHandler.PRINT_ERROR, path, code);
    }
    public ParserSettings(ErrorHandler<ParsingError> errorHandler, String path, String code) {
        this.errorHandler = errorHandler;
        this.path = path;
        this.code = code;

        this.lines = code.split("[\\n\\r]");
        this.lineCount = this.lines.length;
    }

    public ErrorHandler<ParsingError> getErrorHandler() {
        return errorHandler;
    }

    public String getCode() {
        return code;
    }

    public List<Parseable> getParseables() {
        return parseables;
    }

    public String getPath() {
        return path;
    }

    public int lineFromIndex(int index) {
        if (index < 0 || index > code.length()) return -1;
        if (lineCount == 1) {
            return 0;
        }
        String includedCode = code.substring(0, index);
        return includedCode.length() - includedCode.replaceAll("[\\n\\r]", "").length();
    }

    public int getLineCount() {
        return lineCount;
    }

    public String getLine(int i) {
        return lines[i];
    }

    public int getPlaceInLine(int at) {
        int count = 0;
        char[] charArray = code.toCharArray();
        for (int i = 0, charArrayLength = charArray.length; i < charArrayLength; i++) {
            char c = charArray[i];
            if (c == '\n' || c == '\r') {
                count = 0;
            } else {
                count++;
            }
            if (i == at) return count;
        }
        return -1;
    }

}
