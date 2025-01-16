public class Node {
    String key;
    Node next;
    int rank;

    public static String CreateKey(int i, int j) {
        return i + "," + j;
    }

    public Node(String key) {
        this.key = key;
        next = this;
        this.rank = 0;
    }

    public Node(int i, int j) {
        this(CreateKey(i, j));
    }
}