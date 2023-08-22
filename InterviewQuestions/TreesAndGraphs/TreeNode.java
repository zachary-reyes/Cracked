// Taken from Code Library

import java.util.ArrayList;
import java.util.LinkedList;

public class TreeNode {

    public int data;
    public TreeNode left, right, parent;
    private int size = 0;

    public TreeNode(int d) {
        data = d;
        size = 1;
    }

    public void insertInOrder(int d) {
        if (d < data) {
            if (left == null) {
                setLeftChild(new TreeNode(d));
            } else {
                left.insertInOrder(d);
            }
        } else {
            if (right == null) {
                setRightChild(new TreeNode(d));
            } else {
                right.insertInOrder(d);
            }
        }
        size++;
    }

    public TreeNode find(int d) {
        if (d == data) {
            return this;
        } else if (d <= data) {
            return left != null ? left.find(d) : null;
        } else if (d > data) {
            return right != null ? right.find(d) : null;
        }
        return null;
    }
    
    public void setLeftChild(TreeNode left) {
        this.left = left;
        if (left != null) {
            left.parent = this;
        }
    }

    public void setRightChild(TreeNode right) {
        this.right = right;
        if (right != null) {
            right.parent = this;
        }
    }

    /////////////////////
    // Traversals  //////
    /////////////////////
    public static void inOrderTraversal(TreeNode node) {
        if (node != null) {
            inOrderTraversal(node.left);
            visit(node);
            inOrderTraversal(node.right);
        }
    }

    public static void preOrderTraversal(TreeNode node) {
        if (node != null) {
            visit(node);
            preOrderTraversal(node.left);
            preOrderTraversal(node.right);
        }
    }

    public static void postOrderTraversal(TreeNode node) {
        if (node != null) {
            postOrderTraversal(node.left);
            postOrderTraversal(node.right);
            visit(node);
        }
    }

    public static void visit(TreeNode node) {
        System.out.print(node.data + " ");
    }

    /* Minimal Tree: Given a sorted (increasing order) array with unique integer elements, write an algo­rithm to create a binary search tree with minimal height.
     * 
     * Hints: 
     * -> A minimal binary tree has about the same number of nodes on the left of each node as on the right. Let's focus on just the root for now. How would you ensure that about the same number of nodes are on the left of the root as on the right?
      * -> You could implement this by finding the "ideal" next element to add and repeatedly calling insertValue. This will be a bit inefficient, as you would have to repeatedly traverse the tree. Try recursion instead. Can you divide this problem into subproblems?
     * -> Imagine we had a createMinimalTree method that returns a minimal tree for a given array (but for some strange reason doesn't operate on the root of the tree). Could you use this to operate on the root of the tree? Could you write the base case for the function? Great! Then that's basically the entire function.   
     * 
     * Solution #1: Implement simple root.insertNode(int v) method which inserts the value v through a recursive process that starts with the root node....requires traversing the tree for each insertion, giving a total cost of O(log N)
     * 
     * Solution #2: Cut out traversals with createMinimalBST method, which is passed a subsection of the array and returns the root of a minimal tree
     * -> Insert into the tree the middle element of the array
     * -> Insert (into the left subtree) the left subarray elements
     * -> Insert (into the right subtree) the right subarray elements        
     * -> Recurse
     */
    public static TreeNode createMinimalBST(int array[]) {
        return createMinimalBST(array, 0, array.length - 1);
    }

    public static TreeNode createMinimalBST(int arr[], int start, int end) {
        if (end < start) {
            return null;
        }
        int mid = (start + end) / 2;
        TreeNode n = new TreeNode(arr[mid]);
        n.left = createMinimalBST(arr, start, mid - 1);
        n.right = createMinimalBST(arr, mid + 1, end);
        return n;
    }

    /* 4.3 List of Depths: Given a binary tree, design an algorithm which creates a linked list of all the nodes at each depth (e.g., if you have a tree with depth D, you'll have D linked lists). 
     * 
     * Hints: 
     * -> Try modifying a graph search algorithm to track the depth for the root.
     * -> A hash table or array that maps from level number to nodes at that level might also be useful.
     * -> You should be able to come up with an algorithm involving both depth-first search and breadth-first search.
     * 
     *  Solution: Implement simple modification of the pre-order traversal algorithm, adding 'level + 1' to the next recursive call (using Depth First Search)
     * 
     * Check book for BFS solution
     * 
     * Time Complexity: O(N)
     * Space Complexity: O(N)
    */
    public static void createLevelLinkedList(TreeNode root, ArrayList<LinkedList<TreeNode>> lists, int level) {
        if (root == null) return;   // base case

        LinkedList<TreeNode> list = null;
        if (lists.size() == level) {    // level not contained in the list
            list = new LinkedList<TreeNode>();
            /* Levels are always traversed in order. So, if this is the first time we've visited level i, we must have seen levels 0 through i - 1. We can therefore safely add the level at the end */
            lists.add(list);
        } else {
            list = lists.get(level);
        } 
        list.add(root);
        createLevelLinkedList(root.left, lists, level + 1);
        createLevelLinkedList(root.right, lists, level + 1);
    }

    public static ArrayList<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root) {
        ArrayList<LinkedList<TreeNode>> lists = new ArrayList<LinkedList<TreeNode>>();
        createLevelLinkedList(root, lists, 0);
        return lists;
    }

    /* 4.4 Check Balanced: Implement a function to check if a binary tree is balanced. For the purposes of this question, a balanced tree is defined to be a tree such that the heights of the two subtrees of any node never differ by more than one. 
     * 
     * Hints: 
     * -> Think about the definition of a balanced tree. Can you check that condition for a single node? Can you check it for every node?
     * -> If you've developed a brute force solution, be careful about its runtime. If you are computing the height of the subtrees for each node, you could have a pretty inefficient algorithm.
     * -> What if you could modify the binary tree node class to allow a node to store the height of its subtree?
     * -> You don't need to modify the binary tree class to store the height of the subtree. Can your recursive function compute the height of each subtree while also checking if a node is balanced?Try having the function return multiple values.
     * -> Actually, you can just have a single checkHeight function that does both the height computation and the balance check. An integer return value can be used to indicate both.
     * 
     * Time Complexity: O(N)
     * Space Complexity: O(H), where H is the height of the tree
    */
    public static int checkHeight(TreeNode root) {
        if (root == null) return -1; 

        int leftHeight = checkHeight(root.left);
        if (leftHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;  // Pass error up

        int rightHeight = checkHeight(root.right);
        if (rightHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE; // Pass error up

        int heightDiff = leftHeight - rightHeight;
        if (Math.abs(heightDiff) > 1) {
            return Integer.MIN_VALUE;
        } else {    // Recurse
        return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    public static boolean isBalanced(TreeNode root) {
        return checkHeight(root) != Integer.MIN_VALUE;
    }

    /* 4.5 Validate BST: Implement a function to check if a binary tree is a binary search tree.
     * 
     * Hints: 
     * -> If you traversed the tree using an in-order traversal and the elements were truly in the right order, does this indicate that the tree is actually in order? What happens for duplicate elements? If duplicate elements are allowed, they must be on a specific side (usually the left).
     * -> To be a binary search tree, it's not sufficient that the left.value <= current.value < right.value for each node.Every node on the left must be less than the current node, which must be less than all the nodes on the right.
     * -> If every node on the left must be less than or equal to the current node, then this is really the same thing as saying that the biggest node on the left must be less than or equal to the current node.
     * -> Rather than validating the current node's value against leftTree.max and rightTree.min, can we flip around the logic? Validate the left tree's nodes to ensure that they are smaller than current.value.
     * -> Think about the checkBST function as a recursive function that ensures each node is within an allowable (min, max)range. At first,this range is infinite. When we traverse to the left, the min is negative infinity and the max is root. value. Can you implement this recursive function and properly adjust these ranges as you traverse the tree?
     * 
     * Check book for In-Order Traversal Solution (can't handle dups!)
     * 
     * Solution: The Min Max Implementation
     * -> We start with a range of(min = NULL, max = NULL), which the root obviously meets.(NULL indicates that there is no min or max.) We then branch left,checking that these nodes are within the range (min = NULL, max = 20). Then, we branch right, checking that the nodes are within the range (min = 20, max = NULL). We proceed through the tree with this approach. When we branch left, the max gets updated. When we branch right, the min gets updated. If anything fails these checks, we stop and return false.
     * 
     * Time Complexity: O(N), where N is the number of nodes in the tree
     * Space Complexity: O(log N) on a balanced tree
     */
    public static boolean checkBST(TreeNode n) {
        return checkBST(n, null, null);
    }

    public static boolean checkBST(TreeNode n, Integer min, Integer max) {
        if (n == null) {
            return true;
        }
        if ((min != null && n.data <= min) || (max != null && n.data > max)) {
            return false;
        }
        if (!checkBST(n.left, min, n.data) || !checkBST(n.right, n.data, max)) {
            return false;
        }
        return true;
    }

    /* 4.6 Successor: Write an algorithm to find the "next" node (i.e., in-order successor) of a given node in a binary search tree. You may assume that each node has a link to its parent. 
     * 
     * Hints:
     * -> Think about how an in-order traversal works and try to "reverse engineer" it.
     * -> Here's one step of the logic: The successor of a specific node is the leftmost node of the right subtree. What if there is no right subtree, though?
     * 
     * Solution: If n has a right child, its in-order successor is the leftmost node in the right subtree, which is found by calling the leftMostChild method on the right subtree. If n doesn't have a right child, its in-order successor is the first ancestor that is to the left of n.
    */
    public static TreeNode inOrderSucc(TreeNode n) {
        if (n == null) return null;
    
        /* Found right children -> return leftmost node of right subtree. */
        if (n.right != null) {
            return leftMostChild(n.right);
        } else {
            TreeNode q = n;
            TreeNode x = q.parent;
            // Go up until we're on left instead of right
            while (x != null && x.left != q) {
                q = x;
                x = x.parent;
            }
            return x;
        }
    }
    
    // Returns the leftmost child node of the subtree rooted at node n.
    public static TreeNode leftMostChild(TreeNode n) {
        if (n == null) {
            return null;
        }
        while (n.left != null) {
            n = n.left;
        }
        return n;
    }

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
     * -> Careful! Does your algorithm handle the case where only one node exists? What will happen? You might need to tweak the return values a bit.
     * 
     * Solutions: Do our nodes have links to their parents? (1) Yes (2) Yes w/ Better Worst-Case Runtime (3) No (4) Optimized
     */

    /* 4.9 BST Sequences: A binary search tree was created by traversing through an array from left to right and inserting each element. Given a binary search tree with distinct elements, print all possible arrays that could have led to this tree.
     * 
     * Hints: 
     * -> What is the very first value that must be in each array?
     * -> The root is the very first value that must be in every array. What can you say about the order of the values in the left subtree as compared to the values in the right subtree? Do the left subtree values need to be inserted before the right subtree?
     * -> The relationship between the left subtree values and the right subtree values is, essen­tially, anything. The left subtree values could be inserted before the right subtree, or the reverse (right values before left), or any other ordering.
     * -> Break this down into subproblems. Use recursion. If you had all possible sequences for the left subtree and the right subtree, how could you create all possible sequences for the entire tree?
     * 
     * Solutions: (1) Weave subsets and prepend root
    */

    /* 4.10  Check Subtree: Tl and T2 are two very large binary trees, with Tl much bigger than T2. Create an algorithm to determine if T2 is a subtree of Tl. A tree T2 is a subtree of Tl if there exists a node n in Tl such that the subtree of n is identical to T2. That is, if you cut off the tree at node n, the two trees would be identical. 
     * 
     * Hints: 
     * -> If T2 is a subtree of Tl, how will its in-order traversal compare to Tl's? What about its pre-order and post-order traversal?
     * -> The in-order traversals won't tell us much. After all, every binary search tree with the same values (regardless of structure) will have the same in-order traversal. This is what in-order traversal means: contents are in-order. (And if it won't work in the specific case of a binary search tree, then it certainly won't work for a general binary tree.) The pre­ order traversal, however, is much more indicative.
     * -> You may have concluded that if T2.preorderTraversal() is a substring of Tl.preorderTraversal(), then T2 is a subtree of Tl. This is almost true, except that the trees could have duplicate values. Suppose Tl and T2 have all duplicate values but different structures. The pre-order traversals will look the same even though T2 is not a subtree of Tl. How can you handle situations like this?
     * -> Although the problem seems like it stems from duplicate values, it's really deeper than that. The issue is that the pre-order traversal is the same only because there are null nodes that we skipped over (because they're null). Consider inserting a placeholder value into the pre-order traversal string whenever you reach a null node. Register the null node as a "real" node so that you can distinguish between the different structures.
     * -> Alternatively, we can handle this problem recursively. Given a specific node within Tl, can we check to see if its subtree matches T2?
     * 
     * Solution:  A pre-order traversal always starts at the root and, from there, the path we take is entirely defined by the traversal.Therefore, two trees are iden­tical if they have the same pre-order traversal.
     * 
     * o(n + m) time
     * o(n + m) space, where n & m are the number of nodes in T1 and T2, respectively
    */
    public static boolean containsTree(TreeNode t1, TreeNode t2) {
        StringBuilder string1 = new StringBuilder();
        StringBuilder string2 = new StringBuilder();

        getOrderString(t1, string1);
        getOrderString(t2, string2);

        return string1.indexOf(string2.toString()) != -1;
    }

    public static void getOrderString(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("X");     // Add null indicator
            return;
        }
        sb.append(node.data + " ");     // Add root
        getOrderString(node.left, sb);  // Add left
        getOrderString(node.right, sb);  // Add right
    }

    /* 4.11  Random Node: You are implementing a binary tree class from scratch which, in addition to insert, find, and delete, has a method getRandomNode() which returns a random node from the tree. All nodes should be equally likely to be chosen. Design and implement an algorithm for getRandomNode, and explain how you would implement the rest of the methods.
     * 
     * Hints:
     * -> Be very careful in this problem to ensure that each node is equally likely and that your solution doesn't slow down the speed of standard binary search tree algorithms (like insert, find, and delete). Also, remember that even if you assume that it's a balanced binary search tree, this doesn't mean that the tree is full/complete/perfect.
     * -> This is your own binary search tree class, so you can maintain any information about the tree structure or nodes that you'd like (provided it doesn't have other negative implica­ tions, like making insert much slower). In fact, there's probably a reason the interview question specified that it was your own class. You probably need to store some addi­tional information in order to implement this efficiently.
     * -> As a naive "brute force" algorithm, can you use a tree traversal algorithm to implement this algorithm? What is the runtime of this?
     * -> Alternatively, you could pick a random depth to traverse to and then randomly traverse, stopping when you get to that depth. Think this through, though. Does this work?
     * -> Picking a random depth won't help us much. First, there's more nodes at lower depths than higher depths. Second, even if we re-balanced these probabilities, we could hit a "dead end" where we meant to pick a node at depth 5 but hit a leaf at depth 3. Re-balancing the probabilities is an interesting , though.
     * -> A naive approach that many people come up with is to pick a random number between 1 and 3. If it's 1, return the current node. If it's 2, branch left. If it's 3, branch right. This solution doesn't work. Why not? Is there a way you can adjust it to make it work?
     * -> The reason that the earlier solution (picking a random number between 1 and 3) doesn't work is that the probabilities for the nodes won't be equal. For example, the root will be returned with probability X, even if there are 50+ nodes in the tree. Clearly, not all the nodes have probability X, so these nodes won't have equal probability. We can resolve this one issue by picking a random number between 1 and siz e_of_tree instead. This only resolves the issue for the root, though. What about the rest of the nodes?
     * -> The issue with the earlier solution is that there could be more nodes on one side of a node than the other. So, we need to weight the probability of going left and right based on the number of nodes on each side. How does this work, exactly? How can we know the number of nodes?
     * 
     * Option #1: Slow and Working
     * -> Copy all the nodes to an array and return a random element in the array
     * -> O(N) Space and Time, where N is the number of nodes in the tree
     * 
     ** Option #2: Slow and Working
     * -> Maintain an array at all times that lists all the nodes in the tree...removing nodes til we get our node
     * -> O(N) Space and Time, where N is the number of nodes in the tree
     * 
     *** Option #3: Slow and Working
     * -> label all the nodes with an index from 1 to N and label them in binary search tree order and return random index...need to update indices upon insert/delete
     * -> O(N) Space and Time, where N is the number of nodes in the tree
     * 
     **** Option #4: Fast and Not Working
     * -> pick a random depth, and then traverse left/right randomly until we go to that depth. This wouldn' t actually ensure that all nodes are equally likely to be chosen though.
     * 
     ***** Option #5: Fast and Not Working
     * -> Traverse randomly down the tree, at each node: with 1/3 odds return current node, traverse left, or traverse right. Does not distribute the probabilities evenly across the nodes
     * 
     ****** Option #6: Fast and Working
     * -> Resolving the issue with the root: Since we have N nodes, we must return the root node with 1/N probability. (In fact, we must return each node with 1/N probability. After all, we have N nodes and each must have equal probability. The total must be 1 (100%), therefore each must have 1/N probability.)
     * -> the odds of going right should be RIGHT_SIZE * 1/N.
     * -> the odds of going left should be LEFT_SIZE * 1/N.
     * In a balanced tree, O(log N)
     * 
     ******* Option #7: Fast and Working
     -> Random number calls can be expensive. The initial random number call indicates which node (i) to return, and then we're locating the ith node in an in-order traversal. Subtracting LEFT_SIZE from i reflects that,when we go right,we skip over LEFT_SIZE + 1 nodes in the in-order traversal.
     -> In a balanced tree, O(log N)
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
     * 
     * Chekc back for solhngasnkldfkl;asdfjkl;ahjkl; asdfjikl;
    */


    public static void main(String[] args) {
        // Create a sample sorted array
        int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        TreeNode minimalBST = createMinimalBST(sortedArray);

        // Perform in-order traversal to print the tree nodes
        System.out.print("In-order traversal: ");
        inOrderTraversal(minimalBST);
        System.out.println();

        // Perform pre-order traversal to print the tree nodes
        System.out.print("Pre-order traversal: ");
        preOrderTraversal(minimalBST);
        System.out.println();

        // Perform post-order traversal to print the tree nodes
        System.out.print("Post-order traversal: ");
        postOrderTraversal(minimalBST);
        System.out.println();

        // create Linked Lists
        ArrayList<LinkedList<TreeNode>> levelLists = createLevelLinkedList(minimalBST);

        // Print level-linked lists
        int level = 0;
        for (LinkedList<TreeNode> list : levelLists) {
            System.out.print("Level " + level + ": ");
            for (TreeNode node : list) {
                System.out.print(node.data + " ");
            }
            System.out.println();
            level++;
        }

        // Check if the tree is balanced
        boolean balanced = isBalanced(minimalBST);
        System.out.println("Is the tree balanced? " + balanced);

        // Check if the tree is BST
        boolean bst = checkBST(minimalBST);
        System.out.println("Is the tree a BST? " + bst);

        // Find the succesor to the node
        TreeNode node = minimalBST.find(5);
        TreeNode successor = inOrderSucc(node);
        System.out.println("Successor to " + node.data + " is " + successor.data);

        // Test containsTree function
        TreeNode subtree = createMinimalBST(new int[]{1, 2, 3, 4});
        boolean contains = containsTree(minimalBST, subtree);
        System.out.println("Does the main tree contain the subtree? " + contains);

    }


    
}
