package com.me.zev;

import com.me.zev.lang.ParserSettings;
import com.me.zev.lang.ParsingError;

public class Main {

    public static void main(String[] args) {

        ParserSettings settings = new ParserSettings(
                "folder/folder/file.type",

                "move (forward|backward) [for] %duration%|%distance%\n" +
                "move (forward|backward) %duration% [at angle %angle%]\n" +
                "move x [at] speed %decimal% [and] y [at] speed %decimal%\n" +
                "spin %angle%\n" +
                "spin [at] speed %decimal% [for] %duration%\n" +
                "wait [for] %number%(s|ms)"
        );

        for (int i = 0; i < 200; i++) {
            settings.getErrorHandler().handle(new ParsingError((int) (settings.getCode().length() * Math.random()), "This isn't valid syntax LMAO", settings));
        }
    }

}
