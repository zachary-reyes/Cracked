import java.util.LinkedList;
import java.util.Stack;

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

    /* 4.7 Build Order: You are given a list of projects and a list of dependencies (which is a list of pairs of projects, where the second project is dependent on the first project). All of a project's dependencies must be built before the project is. Find a build order that will allow the projects to be built. If there is no valid build order, return an error. 
     * 
     * Hints: 
     * -> Build a directed graph representing the dependencies. Each node is a project and an edge exists from A to B if B depends on A (A must be built before B). You can also build it the other way if it's easier for you.
     * -> Look at this graph. Is there any node you can identify that will definitely be okay to build first?
     * -> If you identify a node without any incoming edges, then it can definitely be built. Find this node (there could be multiple) and add it to the build order. Then, what does this mean for its outgoing edges?
     * -> Once you decide to build a node, its outgoing edge can be deleted. After you've done this, can you find other nodes that are free and clear to build?
     * -> As a totally different approach: Consider doing a depth-first search starting from an arbi­ trary node. What is the relationship between this depth-first search and a valid build order?
     * -> Pick an arbitrary node and do a depth-first search on it. Once we get to the end of a path, we know that this node can be the last one built, since no nodes depend on it. What does this mean about the nodes right before it?
     * 
     * Approach #1: Check book
     * 
     * Approach #2: Using DFS to find the build path (i honestly dont care) this is also known as a topological sort...linearly ordering the vertices in a graph such that for every edge (a,b), a appears before b in the linear order
     * 
     * Both are o(P + D), where P is the number of projects and D is the number of dependency pairs
    */

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
