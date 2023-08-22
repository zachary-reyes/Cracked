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
    }


    
}
