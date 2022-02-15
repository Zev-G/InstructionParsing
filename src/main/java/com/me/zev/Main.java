package com.me.zev;

import com.me.zev.lang.ParsedItem;
import com.me.zev.lang.Parser;
import com.me.zev.lang.ParserSettings;
import com.me.zev.lang.ParsingError;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        ParserSettings settings = new ParserSettings(
                "folder/folder/file.type",

                new BufferedReader(new InputStreamReader(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("test.code"))))
                        .lines()
                        .collect(Collectors.joining("\n"))
        );
        settings.getParseables().addAll(Parser.PARSEABLES);

        ParsedItem[] items = Parser.parse(settings);

        StringBuilder builder = new StringBuilder();
        for (ParsedItem item : items) {
            builder.append(item.getCode());
        }

        System.out.println(builder);
    }

}
