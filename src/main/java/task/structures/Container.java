package task.structures;

public class Container {
    private final int depth;
    private final String product;

    public Container(int n, String prod){
        this.depth = n;
        this.product = prod;
    }

    public int getDepth() {
        return depth;
    }

    public String getProduct() {
        return product;
    }
}
