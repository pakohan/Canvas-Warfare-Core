package gameengine.util;

import java.util.LinkedList;
import java.util.List;

public final class Element {
    private final transient String name;
    private final transient List<Attribute> attributes;
    private final transient List<Element> children;
    private transient String textElement;

    public Element(final String nameParam) {
        this.name = nameParam;

        attributes = new LinkedList<Attribute>();
        children = new LinkedList<Element>();
    }

    public void addAttribute(final Attribute attribute) {
        attributes.add(attribute);
    }

    public void addChild(final Element child) {
        children.add(child);
    }

    public void addTextElement(final String entry) {
        textElement = entry;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("<");

        builder.append(name);

        for (final Attribute a : attributes) {
            builder.append(" ").append(a.toString());
        }

        if (children.isEmpty() && (textElement == null || textElement.length() == 0)) {
            builder.append("/>");
        } else {
            builder.append(">");
            if (children.isEmpty()) {
                builder.append(textElement);
            } else {
                builder.append("\n");
                for (final Element x : children) {
                    builder.append(x.toString());
                }
            }

            builder.append("</");
            builder.append(name);
            builder.append(">");
        }

        builder.append("\n");

        return builder.toString();
    }

    public void addAttribute(final String string, final String attribute) {
        attributes.add(new Attribute(string, attribute));
    }
}
