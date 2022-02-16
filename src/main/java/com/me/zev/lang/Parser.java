package com.me.zev.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// move (forward|backward) [for] %duration%|%distance%
// move (forward|backward) %duration% [at angle %angle%]
// move x [at] speed %decimal% [and] y [at] speed %decimal%
// spin %angle%
// spin [at] speed %decimal% [for] %duration%
// wait [for] %number%(s|ms)
//
public final class Parser {

    public static final List<Parseable> PARSEABLES = new ArrayList<>();
    public static final List<String> IGNORE = Arrays.asList("for", "at", "comment");

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

    private final ParserSettings settings;

    private Parser(ParserSettings settings) {
        this.settings = settings;
    }

    public static ASTNode parse(ParserSettings settings) {
        Parser parser = new Parser(settings);

        return parser.parse();
    }

    private ASTNode parse() {
        ParsedItem[] lexer = lexerParse();
        if (lexer == null) return null;
        lexer = trimLexer(lexer);

        return parseAbstractSyntaxTree(lexer);
    }

    private ASTNode parseAbstractSyntaxTree(ParsedItem[] lexer) {
        ParsedItem item = new ParsedItem(Parseables.name("file"), settings, 0, settings.getCode().length());
        return new ASTNode(item, parseASTPortion(lexer));
    }

    private ASTNode[] parseASTPortion(ParsedItem[] items) {
        if (lexerContainsName("new-line", items)) {
            return separateIntoLines(items);
        }
        // convert all to ASTNodes
        ASTNode[] nodes = new ASTNode[items.length];
        for (int i = 0, itemsLength = items.length; i < itemsLength; i++) {
            nodes[i] = new ASTNode(items[i]);
        }
        return nodes;
    }

    private boolean lexerContainsName(String name, ParsedItem[] lexer) {
        for (ParsedItem item : lexer) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private ASTNode[] separateIntoLines(ParsedItem[] lexer) {
        List<ASTNode> items = new ArrayList<>();
        List<ParsedItem> buffer = new ArrayList<>();
        Parseable line = Parseables.name("new-line");
        for (ParsedItem item : lexer) {
            if (item.getName().equals("new-line")) {
                if (!buffer.isEmpty()) {
                    items.add(new ASTNode(
                                    new ParsedItem(
                                            line, settings, buffer.get(0).getStart(),
                                            buffer.get(buffer.size() - 1).getEnd()),
                                    parseASTPortion(buffer.toArray(new ParsedItem[0]))
                            )
                    );
                    buffer.clear();
                }
            } else {
                buffer.add(item);
            }
        }
        if (!buffer.isEmpty()) {
            items.add(new ASTNode(
                            new ParsedItem(
                                    line, settings, buffer.get(0).getStart(),
                                    buffer.get(buffer.size() - 1).getEnd()),
                            parseASTPortion(buffer.toArray(new ParsedItem[0]))
                    )
            );
        }
        return items.toArray(new ASTNode[0]);
    }

    private ParsedItem[] lexerParse() {
        String code = settings.getCode();
        int at = 0;
        int len = code.length();

        List<ParsedItem> parsed = new ArrayList<>();
        int times = 0;
        main: while (at < len) {
            times++;
            if (times > 1e6) {
                ParsedItem lastParsed = parsed.isEmpty() ? null : parsed.get(parsed.size() - 1);
                settings.getErrorHandler().handle(new ParsingError(at, "Parser got stuck in an infinite loop here." + (lastParsed != null ? " Last parsed \"" + lastParsed.getFormattedCode() + "\" (" + lastParsed.getName() + ")." : ""), settings));
                return null;
            }
            for (Parseable parseable : settings.getParseables()) {
                int match = parseable.matches(code.substring(at));
                int start = at;
                if (match != -1) {
                    at += match;
                    parsed.add(new ParsedItem(parseable, settings, start, at));
                    continue main;
                }
            }
            settings.getErrorHandler().handle(new ParsingError(at, "Couldn't parse, no valid syntax matches this code.", settings));
            return null;
        }
        return parsed.toArray(new ParsedItem[0]);
    }

    private ParsedItem[] trimLexer(ParsedItem[] lexer) {
        List<ParsedItem> items = new ArrayList<>(Arrays.asList(lexer));
        items.removeIf(this::removeLexerItem);
        return items.toArray(new ParsedItem[0]);
    }

    private boolean removeLexerItem(ParsedItem item) {
        String name = item.getName();
        return IGNORE.contains(name);
    }

}
