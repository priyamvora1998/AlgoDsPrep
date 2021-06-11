package btree;

/**
 * @author priyamvora
 * @created 24/02/2021
 */
public class TNode {
    TNode left, right;
    int data;

    public TNode(TNode left, TNode right, int data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }

    @Override
    public String toString() {
        return "TNode{" +
                "left=" + left +
                ", right=" + right +
                ", data=" + data +
                '}';
    }
}
