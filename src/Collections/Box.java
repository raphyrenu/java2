package Collections;

public class Box<T> {
    private T width;
    private T height;
    private T depth;

    public Box() {
    }

    public Box(T width, T height, T depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public T getWidth() {
        return width;
    }

    public void setWidth(T width) {
        this.width = width;
    }

    public T getHeight() {
        return height;
    }

    public void setHeight(T height) {
        this.height = height;
    }

    public T getDepth() {
        return depth;
    }

    public void setDepth(T depth) {
        this.depth = depth;
    }
}


