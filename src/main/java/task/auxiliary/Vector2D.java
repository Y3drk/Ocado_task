package task.auxiliary;

public class Vector2D {
    public final int x;
    public final int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return this.x + " " + this.y;
    }
}
