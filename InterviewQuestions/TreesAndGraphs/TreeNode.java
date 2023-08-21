// Taken from Code Library

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
            preOrderTraversal(node.left);
            preOrderTraversal(node.right);
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
     * -> Try modifying a graph search algorithm to track the depth fro the root.
     * -> A hash table or array that maps from level number to nodes at that level might also be useful.
     * -> You should be able to come up with an algorithm involving both depth-first search and breadth-first search.
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
    }


    
}
