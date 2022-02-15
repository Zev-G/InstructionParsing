package com.me.zev.lang;

import java.util.ArrayList;
import java.util.List;

// move (forward|backward) [for] %duration%|%distance%
// move (forward|backward) %duration% [at angle %angle%]
// move x [at] speed %decimal% [and] y [at] speed %decimal%
// spin %angle%
// spin [at] speed %decimal% [for] %duration%
// wait [for] %number%(s|ms)
//
public final class Parser {

    private static final List<Parseable> PARSEABLES = new ArrayList<>();

    static {
        Token[] commandPrefixes = Token.fromStrings(
                "move", "spin", "wait", "angle", "forward", "upward", "for", "at", "speed",
                "x", "y"
        );
    }

//    private static String[] lexerParse(String text) {
//        int at = 0;
//        int len = text.length();
//
//        List<String> parsed = new ArrayList<>();
//        while (at < len) {
//
//        }
//    }

}
