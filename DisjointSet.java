import java.util.HashMap;

public class DisjointSet {
    public static String CreateKey(int i, int j) {
        return i + "," + j;
    }

    public class Node {
        String key;
        Node next;

        public Node(String key) {
            this.key = key;
            next = this;
        }

        public Node(int i, int j) {
            this(CreateKey(i, j));
        }
    }

    HashMap<String, Node> nodeMap = new HashMap<>();

    public void CreateSingletonSet(int i, int j) {// Creates a Singleton Set comprised of only i,j.
        String key = CreateKey(i, j);
        nodeMap.put(key, new Node(key));
    }

    /*
     * Returns null if i,j not in any of the disjointed sets,
     * otherwise returns the root of the tree its a part of,
     * and makes all itself and all of its ancestors point directly at the root.
     */
    public Node Search(int i, int j) {
        String key = CreateKey(i, j);
        if (!nodeMap.containsKey(key))
            return null;
        Node p = nodeMap.get(key);
        Node root;
        while (p != p.next)
            p = p.next;
        root = p;

        p = nodeMap.get(key);
        Node q;
        while (p != p.next) {
            q = p.next;
            p.next = root;
            p = q;
        }
        return root;
    }

    public Node Union(int i1, int j1, int i2, int j2) {
        Node root1 = Search(i1, j1);
        Node root2 = Search(i2, j2);
        if (root1 != null && root2 != null) {
            root1.next = root2;
            return root2;
        }
        return null;
    }
}