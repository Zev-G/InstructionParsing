package com.me.zev.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// move (forward|backward) [for] %duration%|%distance%
// move (forward|backward) %duration% [at angle %angle%]
// move x [at] speed %decimal% [and] y [at] speed %decimal%
// spin %angle%
// spin [at] speed %decimal% [for] %duration%
// wait [for] %number%(s|ms)
//
public final class Parser {

    public static final List<Parseable> PARSEABLES = new ArrayList<>();

    static {
        Token[] commandPrefixes = Token.fromStrings(
                "move", "spin", "wait", "angle", "forward", "upward", "for", "at", "speed"
        );
        Parseable[] misc = {
                new RegexToken("x|y", "coordinate"),
                new RegexToken("\n|\r", "new-line"),
                Parseables.endsWord(new Token("s", "second")),
                Parseables.endsWord(new Token("ms", "millisecond")),
                Parseables.endsWord(new RegexToken("deg(rees|)", "degrees")).allowLeadingWhiteSpace(),
                new Number(),
                new Comment()
        };
        PARSEABLES.addAll(Arrays.asList(commandPrefixes));
        PARSEABLES.addAll(Arrays.asList(misc));
    }

    public static ParsedItem[] lexerParse(ParserSettings settings) {
        String code = settings.getCode();
        int at = 0;
        int len = code.length();

        List<ParsedItem> parsed = new ArrayList<>();
        int times = 0;
        main: while (at < len) {
            times++;
            if (times > 1e7) {
                settings.getErrorHandler().handle(new ParsingError(at, "Got stuck here. Last parsed with " + parsed.get(parsed.size() - 1) + ".", settings));
                return null;
            }
            for (Parseable parseable : settings.getParseables()) {
                int match = parseable.matches(code.substring(at));
                int start = at;
                if (match != -1) {
                    at += match;
                    parsed.add(new ParsedItem(parseable, code.substring(start, at)));
                    continue main;
                }
            }
            settings.getErrorHandler().handle(new ParsingError(at, "Couldn't parse, no valid syntax matches this code.", settings));
            return null;
        }
        return parsed.toArray(new ParsedItem[0]);
    }

}
