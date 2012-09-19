package gameengine.util;

final class Attribute {
    private final transient String name;
    private final transient String value;

    Attribute(final String nameParam, final String attributeParam) {
        if (nameParam.contains(" ") || nameParam.contains("\t")) {
            throw new IllegalArgumentException("name of the attribute contains whitespaces: " + nameParam);
        }

        this.name = nameParam;
        this.value = attributeParam;
    }

    @Override
    public String toString() {
        return name + "=\"" + value + "\"";
    }
}
