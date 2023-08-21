import java.util.LinkedList;

public class SimpleGraph {
    enum State { Unvisited, Visited, Visiting; }

    private Node[] nodes;

    private class Node {
        public String name;
        public LinkedList<Node> neighbors;
        public State state;

        public Node(String name) {
            this.name = name;
            neighbors = new LinkedList<>();
        }

        public void addAdjacent(Node node) {
            neighbors.add(node);
        }

        public LinkedList<Node> getAdjacent() {
            return neighbors;
        }
    }

    public Node[] getNodes() {
        return nodes;
    }

    /* 4.1 Route Between Nodes: Given a directed graph, design an algorithm to find out whether there is a route between two nodes. 
    * 
    * Hints:
    * -> Two well-known algorithms can do this. What are the tradeoffs between them?
    * 
    * Solution: BFS (iterative implementation)
    */
    boolean search(SimpleGraph g, Node start, Node end) {
        if (start == end) return true;

        LinkedList<Node> q = new LinkedList<Node>();

        for (Node u : g.getNodes()) {
            u.state = State.Unvisited;
        }
        start.state = State.Visiting;
        q.add(start);
        Node u;
        while (!q.isEmpty()) {
            u = q.removeFirst();
            if (u != null) {
                for (Node v : u.getAdjacent()) {
                    if (v.state == State.Unvisited) {
                        if (v == end) {
                            return true;
                        } else {
                            v.state = State.Visiting;
                            q.add(v);
                        }
                    }
                }
                u.state = State.Visited;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SimpleGraph graph = new SimpleGraph();
        Node nodeA = graph.new Node("A");
        Node nodeB = graph.new Node("B");
        Node nodeC = graph.new Node("C");
        Node nodeD = graph.new Node("D");

        nodeA.addAdjacent(nodeB);
        nodeB.addAdjacent(nodeC);
        nodeC.addAdjacent(nodeD);

        graph.nodes = new Node[]{nodeA, nodeB, nodeC, nodeD};

        System.out.println(graph.search(graph, nodeA, nodeD)); // Should print true
        System.out.println(graph.search(graph, nodeD, nodeA)); // Should print false
    }
}


/* pseudo code to implement DFS */
    // void dfs(Node root) {
    //     if (root == null) return;
    //     visit(root);
    //     root.visited = true;
    //     for each (Node n in root.adjacent) {
    //         if (n.visited == false) {
    //             dfs(n);
    //         }
    //     }
    // }

    /* pseudo code to implement BFS */
    // void bfs(Node root) {
    //     Queue queue = new Queue();
    //     root.marked = true;
    //     queue.enqueue(root);    // Add to the end of queue

    //     while (!queue.isEmpty) {
    //         Node r = queue.dequeue();   // Remove from the front of the queue
    //         visit(r);
    //         foreach (Node n in r.adjacent) {
    //             if (n.marked == false) {
    //                 n.marked = true;
    //                 queue.enqueue(n);
    //             }
    //         }
    //     }
    // }
