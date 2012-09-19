package gameengine.util;

import java.util.ArrayList;
import java.util.List;

public final class Matrix<T> {
    private transient int width;
    private transient int height;

    private transient List<T> field;

    public Matrix(final Point dimensions) {
        init(dimensions, new ArrayList<T>(dimensions.getX() * dimensions.getY()));
    }

    public Matrix(final Point dimensions, final List<T> array) {
        init(dimensions, array);
    }

    private void init(final Point dimensions, final List<T> array) {
        width = dimensions.getX();
        height = dimensions.getY();

        field = array;
    }

    public T getElement(final Point coordinate) {
        final int xCoordinate = coordinate.getX();
        final int yCoordinate = coordinate.getY();

        if (xCoordinate < 1 || xCoordinate > width || yCoordinate < 1 || yCoordinate > height) {
            return null;
        }

        return field.get(((yCoordinate - 1) * width) + (xCoordinate - 1));
    }

    public void setElement(final Point coordinate, final T element) {
        final int xCoordinate = coordinate.getX();
        final int yCoordinate = coordinate.getY();

        if (xCoordinate > 0 && xCoordinate <= width && yCoordinate > 0 && yCoordinate <= height) {
            setElement(((yCoordinate - 1) * width) + (xCoordinate - 1), element);
        }
    }

    private void setElement(final int index, final T element) {
        field.set(index, element);
    }

    public int getSize() {
        return field.size();
    }

    public T getElement(final int index) {
        return field.get(index);
    }

    public Point getDimension() {
        return new Point(width, height);
    }

    public void fill() {
        field.clear();
        for (int i = 0; i < width * height; i++) {
            field.add(null);
        }
    }
}
