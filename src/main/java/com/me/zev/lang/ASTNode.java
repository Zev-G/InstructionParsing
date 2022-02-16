package com.me.zev.lang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ASTNode {

    private List<ASTNode> children;
    private final ParsedItem item;

    public ASTNode(ParsedItem item, ASTNode... children) {
        this(item, Arrays.asList(children));
    }
    public ASTNode(ParsedItem item, Collection<ASTNode> children) {
        if (children != null && !children.isEmpty()) {
            this.children = new ArrayList<>(children);
        }
        this.item = item;
    }

    public List<ASTNode> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        return this.children;
    }

    public ASTNode get(int i) {
        if (children == null) return null;
        return children.get(i);
    }

    public boolean isEmpty() {
        if (children == null) return true;
        return children.isEmpty();
    }

    public ParsedItem getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "ASTNode{" +
                "children=" + children +
                ", item=" + item +
                '}';
    }

    public String toPrettyString() {
        return toPrettyString(0);
    }
    public String toPrettyString(int indent) {
        if (children == null || children.isEmpty()) {
            return "  ".repeat(indent) + item.getName() + " \"" + item.getFormattedCode() + " \"";
        } else {
            return "  ".repeat(indent) +
                    item.getName() + " {\n" +
                    children.stream().map(child -> child.toPrettyString(indent + 1)).collect(Collectors.joining("\n")) +
                    "\n" + "  ".repeat(indent) + "}";
        }
    }

}
