package com.me.zev.lang;

public class ParsingError {

    // CAN be equal to each other.
    private final int at;
    private final String explanation;
    private final ParserSettings settings;

    public ParsingError(int at, String explanation, ParserSettings settings) {
        this.at = at;
        this.explanation = explanation;
        this.settings = settings;
    }

    public String getFormattedErrorMessage() {
        int line = settings.lineFromIndex(at);
        if (line < -1) return "ERROR FORMATTING ERROR MESSAGE\n" + getSimpleErrorMessage();
        int placeInLine = settings.getPlaceInLine(at);

        int startLine = Math.max(0, line - 2);

        int maxLen = String.valueOf(line + 1).length();
        StringBuilder builder = new StringBuilder();

        builder.append("Error on line ").append(line + 1).append(" at position ").append(placeInLine);
        if (settings.getPath() != null) {
            builder.append(" in file \"").append(settings.getPath()).append("\"");
        }
        builder.append(".\n\n");

        for (int i = startLine; i <= line; i++) {
            String lineNumber = String.valueOf(i + 1);
            if (lineNumber.length() < maxLen) {
                lineNumber = lineNumber + " ".repeat(maxLen - lineNumber.length());
            }
            builder.append(lineNumber).append(" | ").append(settings.getLine(i)).append("\n");
        }
        builder.append(" ".repeat(placeInLine - 1 + maxLen + 3)).append("^").append("\n").append(explanation).append("\n");

        return builder.toString();
    }

    public String getSimpleErrorMessage() {
        return "Error at index " + at + (settings.getPath() != null ? "in file " + settings.getPath() : "") + ".\n" + explanation;
    }

}
