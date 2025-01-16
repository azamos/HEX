import java.util.HashMap;

public class DisjointSet {

    HashMap<String, Node> nodeMap = new HashMap<>();

    public void CreateSingletonSet(int i, int j) {// Creates a Singleton Set comprised of only i,j.
        String key = Node.CreateKey(i, j);
        if (!nodeMap.containsKey(key))
            nodeMap.put(key, new Node(key));
    }

    /* a recursive aux method to assist Search method below */
    private Node find(Node node) {
        if (node != node.next) {
            node.next = find(node.next);
        }
        return node.next;
    }

    /*
     * Returns null if i,j not in any of the disjointed sets,
     * otherwise returns the root of the tree its a part of,
     * and makes all itself and all of its ancestors point directly at the root.
     */
    public Node Search(int i, int j) {
        String key = Node.CreateKey(i, j);
        if (!nodeMap.containsKey(key))
            return null;
        return find(nodeMap.get(key));
    }

    public Node Union(int i1, int j1, int i2, int j2) {
        Node root1 = Search(i1, j1);
        Node root2 = Search(i2, j2);
        if (root1 == null || root2 == null)
            return null;
        if (root1 == root2)
            return root1;
        if (root1.rank < root2.rank) {
            root1.next = root2;
            return root2;
        }
        if (root2.rank == root1.rank)
            root1.rank++;

        root2.next = root1;
        return root1;
    }
}