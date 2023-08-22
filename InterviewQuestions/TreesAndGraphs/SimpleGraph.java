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

    /* 4.8 First Common Ancestor: Design an algorithm and write code to find the first common ancestor of two nodes in a binary tree. Avoid storing additional nodes in a data structure. NOTE: This is not necessarily a binary search tree.
     * 
     * Hints:
     * -> If each node has a link to its parent, we could leverage the approach from question 2.7 on page 95. However, our interviewer might not let us make this assumption.
     * -> The first common ancestor is the deepest node such that p and q are both descendants. Think about how you might identify this node.
     * -> How would you figure out if p is a descendent of a node n?
     * -> Start with the root. Can you identify if root is the first common ancestor? If it is not, can you identify which side of root the first common ancestor is on?
     * -> Try a recursive approach. Check if p and q are descendants of the left subtree and the right subtree. If they are descendants of different subtrees, then the current node is the first common ancestor. If they are descendants of the same subtree, then that subtree holds the first common ancestor. Now, how do you implement this efficiently?
     * -> In the more naive algorithm, we had one method that indicated if x is a descendent of n, and another method that would recurse to find the first common ancestor. This is repeatedly searching the same elements in a subtree. We should merge this into one firstCommonAncestor function. What return values would give us the information we need?
     * -> The firstCommonAncestor function could return the first common ancestor (if p and q are both contained in the tree), p if p is in the tree and not q, q if q is in the tree and not p, and null otherwise.
     * -> Careful! Does your algorithm handle the case where only one node exists? What will happen?You might need to tweak the return values a bit.
     * 
     * 
     */

    /* 4.9 BST Sequences: A binary search tree was created by traversing through an array from left to right and inserting each element. Given a binary search tree with distinct elements, print all possible arrays that could have led to this tree.
     * 
     * Hints: 
     * -> What is the very first value that must be in each array?
     * -> The root is the very first value that must be in every array. What can you say about the order of the values in the left subtree as compared to the values in the right subtree? Do the left subtree values need to be inserted before the right subtree?
     * -> The relationship between the left subtree values and the right subtree values is, essen­ tially, anything. The left subtree values could be inserted before the right subtree, or the reverse (right values before left), or any other ordering.
     * -> Break this down into subproblems. Use recursion. If you had all possible sequences for the left subtree and the right subtree, how could you create all possible sequences for the entire tree?
    */

    /* 4.10  Check Subtree: Tl and T2 are two very large binary trees, with Tl much bigger than T2. Create an algorithm to determine if T2 is a subtree of Tl. A tree T2 is a subtree of Tl if there exists a node n in Tl such that the subtree of n is identical to T2. That is, if you cut off the tree at node n, the two trees would be identical. 
     * 
     * Hints: 
     * -> If T2 is a subtree of Tl, how will its in-order traversal compare to Tl's? What about its pre-order and post-order traversal?
     * -> The in-order traversals won't tell us much. After all, every binary search tree with the same values (regardless of structure) will have the same in-order traversal. This is what in-order traversal means: contents are in-order. (And if it won't work in the specific case of a binary search tree, then it certainly won't work for a general binary tree.) The pre­ order traversal, however, is much more indicative.
     * -> You may have concluded that if T2.preorderTraversal() is a substring of Tl. preorderTraversal(), then T2 is a subtree of Tl. This is almost true, except that the trees could have duplicate values. Suppose Tl and T2 have all duplicate values but different structures. The pre-order traversals will look the same even though T2 is not a subtree of Tl. How can you handle situations like this?
     * -> Although the problem seems like it stems from duplicate values, it's really deeper than that. The issue is that the pre-order traversal is the same only because there are null nodes that we skipped over (because they're null). Consider inserting a placeholder value into the pre-order traversal string whenever you reach a null node. Register the null node as a "real" node so that you can distinguish between the different structures.
     * -> Alternatively, we can handle this problem recursively. Given a specific node within Tl, can we check to see if its subtree matches T2?
    */

    /* 4.11  Random Node: You are implementing a binary tree class from scratch which, in addition to insert, find, and delete, has a method getRandomNode() which returns a random node from the tree. All nodes should be equally likely to be chosen. Design and implement an algorithm for getRandomNode, and explain how you would implement the rest of the methods.
     * 
     * Hints:
     * -> Be very careful in this problem to ensure that each node is equally likely and that your solution doesn't slow down the speed of standard binary search tree algorithms (like insert, find, and delete). Also, remember that even if you assume that it's a balanced binary search tree, this doesn't mean that the tree is full/complete/perfect.
     * -> This is your own binary search tree class, so you can maintain any information about the tree structure or nodes that you'd like (provided it doesn't have other negative implica­ tions, like making insert much slower). In fact, there's probably a reason the interview question specified that it was your own class. You probably need to store some addi­ tional information in order to implement this efficiently.
     * -> As a naive "brute force" algorithm, can you use a tree traversal algorithm to implement this algorithm? What is the runtime of this?
     * -> Alternatively, you could pick a random depth to traverse to and then randomly traverse, stopping when you get to that depth. Think this through, though. Does this work?
     * -> Picking a random depth won't help us much. First, there's more nodes at lower depths than higher depths. Second, even if we re-balanced these probabilities, we could hit a "dead end" where we meant to pick a node at depth 5 but hit a leaf at depth 3. Re-balancing the probabilities is an interesting , though.
     * -> A naive approach that many people come up with is to pick a random number between 1 and 3. If it's 1, return the current node. If it's 2, branch left. If it's 3, branch right. This solution doesn't work. Why not? Is there a way you can adjust it to make it work?
     * -> The reason that the earlier solution (picking a random number between 1 and 3) doesn't work is that the probabilities for the nodes won't be equal. For example, the root will be returned with probability X, even if there are 50+ nodes in the tree. Clearly, not all the nodes have probability X, so these nodes won't have equal probability. We can resolve this one issue by picking a random number between 1 and siz e_of_tree instead. This only resolves the issue for the root, though. What about the rest of the nodes?
     * -> The issue with the earlier solution is that there could be more nodes on one side of a node than the other. So, we need to weight the probability of going left and right based on the number of nodes on each side. How does this work, exactly? How can we know the number of nodes?
    */

    /* 4.12  Paths with Sum: You are given a binary tree in which each node contains an integer value (which might be positive or negative). Design an algorithm to count the number of paths that sum to a given value. The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent nodes to child nodes).
     * 
     * Hints:
     * -> Try simplifying the problem. What if the path had to start at the root?
     * -> Don't forget that paths could overlap. For example, if you're looking for the sum 6, the paths 1->3->2 and 1->3->2->4->-6->2 are both valid.
     * -> If each path had to start at the root, we could traverse all possible paths starting from the root. We can track the sum as we go, incrementing totalPaths each time we find a path with our target sum. Now, how do we extend this to paths that can start anywhere? Remember: Just get a brute-force algorithm done. You can optimize later.
     * -> To extend this to paths that start anywhere, we can just repeat this process for all nodes.
     * -> If you've designed the algorithm as described thus far, you'll have an O(N log N) algorithm in a balanced tree.This is because there are N nodes,each of which is at depth O(log N) at worst. A node is touched once for each node above it. Therefore, the N nodes will be touched O( log N) time. There is an optimization that will give us an O(N) algorithm.
     * -> What work is duplicated in the current brute-force algorithm?
     * -> Consider each path that starts from the root (there are N such paths) as an array. What our brute-force algorithm is really doing is taking each array and finding all contiguous subsequences that have a particular sum. We're doing this by computing all subarrays and their sums. It might be useful to just focus on this little subproblem. Given an array, how would you find all contiguous subsequences with a particular sum? Again, think about the duplicated work in the brute-force algorithm.
     * -> We are looking for subarrays with sum targetSum. Observe that we can track in constant time the value of running Sum i,where this is the sum from element O through element i. For a subarray of element i through element j to have sum targetSum, runningSum i-1 + targetSum must equal runningSum j (try drawing a picture of an array or a number line). Given that we can track the runningSum as we go, how can we quickly look up the number of indices i where the previous equation is true?
     * -> Try using a hash table that maps from a running Sumvalue to he number of elements with this runningSum.
     * -> Once you've solidified the algorithm to find all contiguous subarrays in an array with a given sum, try to apply this to a tree. Remember that as you're traversing and modifying the hash table, you may need to "reverse the damage" to the hash table as you traverse back up.
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
