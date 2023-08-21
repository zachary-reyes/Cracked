// doubly linked list implementation from the Code Library in the book

import java.util.HashSet;
import java.util.LinkedList;

public class LinkedListNode {

    public LinkedListNode next, prev, last;
    public int data;

    public LinkedListNode(int d, LinkedListNode n, LinkedListNode p){ 
    data = d;
    setNext(n);
    setPrevious(p);
    }

    public LinkedListNode(int d) {
    data = d;
    }

    public LinkedListNode() {}

    public void setNext(LinkedListNode n) {
        next = n;
        if (this == last) {
            last = n;
        }
        if (n != null && n.prev != this) {
            n.setPrevious(this);
        }
    }

    public void setPrevious(LinkedListNode p) {
        prev = p;
        if (p != null && p.next != this) {
            p.setNext(this);
        }
    }

    public LinkedListNode clone() {
        LinkedListNode next2 = null;
        if (next != null) {
            next2 = next.clone();
        }
        LinkedListNode head2 = new LinkedListNode(data, next2, null); 
        return head2;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    /* 
    * 2.1 Remove Dups: Write code to remove duplicates from an unsorted linked list.
    * 
    * Hints: 
    * -> Have you tried a hash table? You should be able to do this in a single pass of the linked list.
    * -> Without extra space, you'll need O(N^2) time. Try using two pointers, where the seond one searches ahead of the first one.
    * 
    *  Time Complexity: O(N)
    * Space Complexity: O(N)
    */
    public void deleteDups() {
        HashSet<Integer> set = new HashSet<Integer>();
        LinkedListNode previous = null;
        LinkedListNode n = this;
        while (n != null) {
            if (set.contains(n.data)) {
                previous.next = n.next;
            } else {
                set.add(n.data);
                previous = n;
            }
            n = n.next;
        }
    }

    /* 
     * FOLLOW UP: How would you solve this problem if a temporary buffer is not allowed?
     *  
     * "The Runner Technique"
     * 
     *  Time Complexity: O(N^2)
     * Space Complexity: O(1)
     */
    public void deleteDupsRunner() {
        LinkedListNode n = this;
        while (n != null) {
            /* Remove all future nodes that have the same value */
            LinkedListNode runner = n;
            while (runner.next != null) {
                if (runner.next.data == n.data) {
                    runner.next = runner.next.next;
                } else {
                    runner = runner.next;
                }
            }
            n = n.next;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    /* 
    * 2.2 Return Kth to Last: Implement an algorithm to find the kth to last element of a singly linked list.
    * 
    * Hints: 
    * -> What if you knew the linked list size? What is the difference between finding the Kth-to­ last element and finding the Xth element?
    * -> If you don't know the linked list size, can you compute it? How does this impact the runtime?
    * -> Try implementing it recursively. If you could find the (K-1)th to last element, can you find the Kth element?
    * -> You might find it useful to return multiple values. Some languages don't directly support this, but there are workarounds in essentially any language. What are some of those workarounds?
    * -> Can you do it iteratively? Imagine if you had two pointers pointing to adjacent nodes and they were moving at the same speed through the linked list. When one hits the end of the linked list, where will the other be?
    *
    * Solution #1: If linked list size is known 
        If the size of the linked list is known,then the kth to last element is the(length - k)th element. We can just iterate through the linked list to find this element. Because this solution is so trivial, we can almost be sure that this is not what the interviewer intended.
    * 
    * Solution #2: Recursive
        This algorithm recurses through the linked list. When it hits the end, the method passes back a counter set to 0. Each parent call adds 1 to this counter. When the counter equals k, we know we have reached the kth to last element of the linked list.
    * 
    *  Time Complexity: O(N)
    * Space Complexity: O(N)
    */
    public static int printKthToLast(LinkedListNode head, int k) {
        if (head == null) {
            return 0;
        }
        int index = printKthToLast(head.next, k) + 1;
        if (index == k) {
            System.out.println(k + "th to last node is " + head.data);
        }
        return index;
    }

    /* Solution #3: Iterative
     *  More optimal, but less straightforward....
     *  Place two pointers, p1 and p2, k nodes apart in the linked list, starting with p2 at the head.
     *  Moving at the same pace, p1 will hit the end of the last after (length - k steps), and p2 will be k nodes from the end
     * 
     *  Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    public static LinkedListNode nthToLast(LinkedListNode head, int k) {
        LinkedListNode p1 = head;
        LinkedListNode p2 = head;

        /* Move p1 k nodes into the list */
        for (int i = 0; i < k; i++) {
            if (p1 == null) return null; // out of bounds
            p1 = p1.next;
        }

        /* Move them at the same pace. When p1 hits the end, p2 will be at the right element */
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p2;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 2.3 Delete Middle Node: Implement an algorithm to delete a node in the middle (i.e. any node but the first and last node, not necessarily the exact middle) of a singly linked list, given only access to that node.
     * 
     * Hints
     * -> Picture the list 1->5->9->12. Removing 9 would make it look like 1->5->12. You only have access to the 9 node. Can you make it look like the correct answer?
     * 
     *  Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public static boolean deleteNode(LinkedListNode n) {
        if (n == null || n.next == null) {
            return false; // failure
        }
        LinkedListNode next = n.next;
        n.data = next.data;
        n.next = next.next;
        return true;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 2.4 Partition: Write code to partition a linked list around a value x, such that all nodes less than x come before all nodes greater than or equal to x. If x is contained within the list, the values of x only need to be after the elements less than x (see below). The partition element x can appear anywhere in the "right partition"; it does not need to appear between the left and right partitions.
     * 
     * Hints
     * -> There are many solutions to this problem, most of which are equally optimal in runtime. Some have shorter, cleaner code than others. Can you brainstorm different solutions?
     * -> Consider that the elements don't have to stay in the same relative order. We only need to ensure that elements less than the pivot must be before elements greater than the pivot. Does that help you come up with more solutions?
     * 
     * Solution #1: Add elements less than the pivot to a list, Add elements greater than or equal to pivot to another list, and then merge them together
     * 
     * Solution #2: Rearrange the elements by growing the list at the head and tail
     *      Start a "new" list (using the existing nodes) and add elements bigger than the pivot to the tail and elements less than the pivot at the head.
     * 
     *  Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    public static LinkedListNode partition(LinkedListNode node, int x) {
        LinkedListNode head = node;
        LinkedListNode tail = node;

        while (node != null) {
            LinkedListNode next = node.next;
            if (node.data < x) {
                /* Insert node at head */
                node.next = head;
                head = node;
            } else {
                /* Insert node at tail */
                tail.next = node;
                tail = node;
            }
            node = next;
        }
        tail.next = null;

        // The head has changed, so we need to return it to the user
        return head;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 2.5 Sum Lists: You have two numbers represented by a linked list, where each node contains a single digit.The digits are stored in reverse order, such that the 1 's digit is at the head of the list. Write a function that adds the two numbers and returns the sum as a linked list.
     * 
     * FOLLOW UP: Suppose the digits are stored in forward order. Repeat the above problem.
     * 
     * Hints
     * -> Of course, you could convert the linked lists to integers, compute the sum, and then convert it back to a new linked list. If you did this in an interview, your interviewer would likely accept the answer, and then see if you could do this without converting it to a number and back.
     * -> Try recursion. Suppose you have two lists, A = 1->5->9 (representing 951) and B 2->3->6->7 (representing 7632), and a function that operates on the remainder of the lists (5->9 and 3->6->7). Could you use this to create the sum method? What is the relationship between sum(l->5->9, 2->3->6->7) and sum(5->9, 3->6->7)?
     * -> Make sure you have considered linked lists that are not the same length.
     * -> Careful! Does your algorithm handle the case where only one node exists? What will happen?You might need to tweak the return values a bit.
     * -> For the follow-up question: The issue is that when the linked lists aren't the same length, the head of one linked list might represent the 1OOO's place while the other represents the 1O's place. What if you made them the same length? Is there a way to modify the linked list to do that, without changing the value it represents?
     * 
     * Solution #1: mimic this process recursively by adding node by node,carrying over any "excess" data to the next node. 
     * 
     *  Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public static LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2, int carry) {
        if (l1 == null && l2 == null && carry == 0) {
            return null;
        }

        LinkedListNode result = new LinkedListNode();
        int value = carry;
        if (l1 != null) {
            value += l1.data;
        }
        if (l2 != null) {
           value += l2.data; 
        }

        result.data = value % 10; /* Second digit of number */

        /* Recurse */
        if (l1 != null || l2 != null) {
            LinkedListNode more = addLists(l1 == null ? null : l1.next,
                                           l2 == null ? null : l2.next,
                                           value >= 10 ? 1 : 0);
            result.setNext(more);                                           
        }
        return result;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 2.6 Palindrome: Implement a function to check if a linked list is a palindrome.
     * 
     * Hints
     * -> A palindrome is something which is the same when written forwards and backwards. What if you reversed the linked list?
     * -> Try using a stack.
     * -> Assume you have the length of the linked list. Can you implement this recursively?
     * -> In the recursive approach (we have the length of the list), the middle is the base case: isPermutation (middle) is true. The node x to the immediate left of the middle: What can that node do to check if x->middle->y forms a palindrome? Now suppose that checks out. What about the previous node a? If x->middle->y is a palindrome, how can it check that a->x->middle->y->b is a palindrome?
     * -> Go back to the previous hint. Remember: There are ways to return multiple values. You can do this with a new class.
     * 
     * Solution #1: Reverse and Compare (code shown below)
     * 
     * Solution #2: Iterative Approach: Using a stack (reference pg. 218)
     * 
     * Solution #3: Recursive Approach (reference pg. 218)
     * 
     *  Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public boolean isPalindrome(LinkedListNode head) {
        LinkedListNode reversed = reverseAndClone(head);
        return isEqual(head, reversed); 
    }

    LinkedListNode reverseAndClone(LinkedListNode node) {
        LinkedListNode head = null;
        while (node != null) {
            LinkedListNode n = new LinkedListNode(node.data); // Clone
            n.next = head;
            head = n;
            node = node.next;
        }
        return node;
    }

    boolean isEqual(LinkedListNode one, LinkedListNode two) {
        while (one != null && two != null) {
            if (one.data != two.data) {
                return false;
            }
            one = one.next;
            two = two.next;
        }
        return one == null && two == null;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * 2.7 Intersection: Given two (singly) linked lists, determine if the two lists intersect. Return the inter­ secting node. Note that the intersection is defined based on reference, not value.That is, if the kth node of the first linked list is the exact same node (by reference) as the jth node of the second linked list, then they are intersecting.
     * 
     * Hints
     * -> You can do this in O(A+B) time and 0(1) additional space. That is, you do not need a hash table (although you could do it with one).
     * -> Examples will help you. Draw a picture of intersecting linked lists and two equivalent
linked lists (by value) that do not intersect.
     * -> Focus first on just identifying if there's an intersection.
     * -> Observe that two intersecting linked lists will always have the same last node. Once they intersect, all the nodes after that will be equal.
     * -> You can determine if two linked lists intersect by traversing to the end of each and comparing their tails.
     * -> Now, you need to find where the linked lists intersect. Suppose the linked lists were the same length. How could you do this?
     * -> If the two linked lists were the same length, you could traverse forward in each until you found an element in common. Now, how do you adjust this for lists of different lengths?
     * -> Try using the difference between the lengths of the two linked lists.
     * -> If you move a pointer in the longer linked list forward by the difference in lengths, you can then apply a similar approach to the scenario when the linked lists are equal.
     * 
     * Solution: 
     * 1. Run through each linked list to get the lengths and the tails.
     * 2. Compare the tails. If they are different (by reference, not by value), return immediately. There is no inter-section.
     * 3. Set two pointers to the start of each linked list.
     * 4. On the longer linked list, advance its pointer by the difference in lengths. 
     * 5. Now, traverse on each linked list until the pointers are the same.
     * 
     *  Time Complexity: O(A + B)
     * Space Complexity: O(1)
     */
    LinkedListNode findIntersection(LinkedListNode list1, LinkedListNode list2) {
        if (list1 == null || list2 == null) return null;

        /* Get tail and sizes */
        Result result1 = getTailAndSize(list1);
        Result result2 = getTailAndSize(list2);

        /* If different tail nodes, then there's no intersection */
        if (result1.tail != result2.tail) {
            return null;
        }

        /* Set pointers to the start of each linked list */
        LinkedListNode shorter = result1.size < result2.size ? list1 : list2;
        LinkedListNode longer = result1.size < result2.size ? list2 : list1;

        /* Advance the pointer for the longer linked list by difference in lengths */
        longer = getKthNode(longer, Math.abs(result1.size - result2.size));

        /* Move both pointers until you have a collision */
        while (shorter != longer) {
            shorter = shorter.next;
            longer = longer.next;
        }

        /* Return either one */
        return longer;
    }

    class Result {
        public LinkedListNode tail;
        public int size;

        public Result(LinkedListNode tail, int size) {
            this.tail = tail;
            this.size = size;
        }
    }

    Result getTailAndSize(LinkedListNode list) {
        if (list == null) return null;

        int size = 1;
        LinkedListNode current = list;
        while (current.next != null) {
            size++;
            current = current.next;
        }
        return new Result(current, size);
    }

    LinkedListNode getKthNode(LinkedListNode head, int k) {
        LinkedListNode current = head;
        while (k > 0 && current != null) {
            current = current.next;
            k--;
        }
        return current;
    }

    /* 
     * 2.8 Loop Detection: Given a circular linked list, implement an algorithm that returns the node at the beginning of the loop.
     * 
     * Circular Linked List: A (corrupt) linked list in which a node's next pointer points to an earlier node, so as to make a loop in the linked list.
     * 
     * Hints
     * -> There are really two parts to this problem. First, detect if the linked list has a loop. Second, figure out where the loop starts.
     * -> To identify if there's a cycle, try the "runner" approach described on page 93. Have one pointer move faster than the other.
     * -> You can use two pointers, one moving twice as fast as the other. If there is a cycle, the two pointers will collide. They will land at the same location at the same time. Where do they land? Why there?
     * -> If you haven't identified the pattern of where the two pointers start, try this: Use the linked list 1->2->3->4->5->6->7->8->9->?, where the ? links to another node. Try making the ? the first node (that is, the 9 points to the 1 such that the entire linked list is a loop). Then make the ? the node 2. Then the node 3. Then the node 4. What is the pattern? Can you explain why this happens?
     * 
     * Solution: 
     * 1. Create two pointers, FastPointer & SlowPointer
     * 2. Move FastPointer at a rate of 2 steps and SlowPointer at a rate of 1 step
     * 3. When they collide, move SlowPointer to LinkedListHead. Keep FastPointer where it is.
     * 4. Move SlowPointer and FastPointer at a rate of one step. Return the new collision spot.
     * 
     *  Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    LinkedListNode FindBeginning(LinkedListNode head) {
        LinkedListNode slow = head;
        LinkedListNode fast = head;

        /* Find meeting point. This will be LOOP_SIZE - k steps into the linked list. */
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {  // Collision
                break;
            }
        }
        /* Error check - no meeting point, and therefore no loop */
        if (fast == null || fast.next == null) {
            return null;
        }

        /* Move slow to Head. Keep fast at Meeting Point. Each are k steps from the Loop Start. If they move at the same pace, they must meet at Loop Start. */
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        /* Both now point to the start of the loop. */
        return fast;
    }

    public static void main(String[] args) {
        LinkedListNode head2 = new LinkedListNode(3);
        head2.setNext(new LinkedListNode(5));
        head2.next.setNext(new LinkedListNode(8));
        head2.next.next.setNext(new LinkedListNode(5));
        head2.next.next.next.setNext(new LinkedListNode(10));
        head2.next.next.next.next.setNext(new LinkedListNode(2));
        head2.next.next.next.next.next.setNext(new LinkedListNode(1));

        LinkedListNode head = new LinkedListNode(1);
        head.setNext(new LinkedListNode(2));
        head.next.setPrevious(head);
        head.next.setNext(new LinkedListNode(2));
        head.next.next.setPrevious(head.next);
        head.next.next.setNext(new LinkedListNode(3));
        head.next.next.next.setPrevious(head.next.next);

        // Print the doubly linked list
        printDoublyLinkedList(head);    

        // head.deleteDups();
        head.deleteDupsRunner();

        printDoublyLinkedList(head);  
        printKthToLast(head, 3);

        LinkedListNode nth = nthToLast(head, 3);
        System.out.println("3rd to last node is " + nth.data);

        boolean deleted = deleteNode(head.next);
        System.out.println("Can delete 2? " + deleted);
        printDoublyLinkedList(head); 
        
        printDoublyLinkedList(head2);
        LinkedListNode partitioned = partition(head2, 5);
        printDoublyLinkedList(partitioned);
    }

    public static void printDoublyLinkedList(LinkedListNode head) {
        LinkedListNode current = head;
        while (current != null) {
            System.out.print(current.data + " <-> ");
            current = current.next;
        }
        System.out.println("null");
    }
    
}
    
